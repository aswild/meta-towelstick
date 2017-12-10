#!/bin/sh

PATH=/sbin:/bin:/usr/sbin:/usr/bin

# defaults, which may get overridden below
ROOT_MOUNT="/rootfs"
TSROOT_MOUNT="/boot"
ROOT=""
TSROOT="LABEL=TOWELSTICK"
ROOT_IMAGE="rootfs.img"

MOUNT="/bin/mount"
UMOUNT="/bin/umount"

read_args() {
    [ -z "$CMDLINE" ] && CMDLINE=`cat /proc/cmdline`
    for arg in $CMDLINE; do
        optarg=`expr "x$arg" : 'x[^=]*=\(.*\)'`
        case $arg in
            root=*)         ROOT=$optarg ;;
            tsroot=*)       TSROOT=$optarg ;;
            rootimage=*)    ROOT_IMAGE=$optarg ;;
            rootfstype=*)   modprobe $optarg 2> /dev/null ;;
            debugshell*)    debugshell=y ;;
        esac
    done
}

# Copied from initramfs-framework. The core of this script probably should be
# turned into initramfs-framework modules to reduce duplication.
udev_daemon() {
    OPTIONS="/sbin/udev/udevd /sbin/udevd /lib/udev/udevd /lib/systemd/systemd-udevd"

    for o in $OPTIONS; do
        if [ -x "$o" ]; then
            echo $o
            return 0
        fi
    done

    return 1
}

_UDEV_DAEMON=`udev_daemon`

early_setup() {
    mkdir -p /proc
    mkdir -p /sys
    mount -t proc proc /proc
    mount -t sysfs sysfs /sys
    mount -t devtmpfs none /dev

    # support modular kernel
    modprobe isofs 2> /dev/null

    mkdir -p /run
    mkdir -p /run/media
    mkdir -p /var/run

    $_UDEV_DAEMON --daemon
    udevadm trigger --action=add
    udevadm settle --timeout=10
}

boot_live_root() {
    # Watches the udev event queue, and exits if all current events are handled
    udevadm settle --timeout=3
    # Kills the current udev running processes, which survived after
    # device node creation events were handled, to avoid unexpected behavior
    killall -9 "${_UDEV_DAEMON##*/}" 2>/dev/null

    # Don't run systemd-update-done on systemd-based live systems
    # because it triggers a slow rebuild of ldconfig caches.
    touch ${ROOT_MOUNT}/etc/.updated ${ROOT_MOUNT}/var/.updated

    # Allow for identification of the real root even after boot
    mkdir -p ${ROOT_MOUNT}/boot
    mount -n --move ${TSROOT_MOUNT} ${ROOT_MOUNT}${TSROOT_MOUNT}

    mount -n --move /proc ${ROOT_MOUNT}/proc
    mount -n --move /sys  ${ROOT_MOUNT}/sys
    mount -n --move /dev  ${ROOT_MOUNT}/dev

    cd $ROOT_MOUNT

    # busybox switch_root supports -c option
    exec switch_root -c /dev/console ${ROOT_MOUNT} /sbin/init ${CMDLINE} ||
        fatal "Couldn't switch_root, dropping to shell"
}

fatal() {
    echo $1 >/init-fatal.log
    echo $1 >$CONSOLE
    echo >$CONSOLE
    exec sh
}

run_or_die() {
    local output ret
    output=`"$@" 2>&1`
    ret=$?
    echo "$output"
    if [ $ret != 0 ]; then
        fatal "Command '$*' failed. Output was: '$output'"
    fi
}

early_setup
[ -z "$CONSOLE" ] && CONSOLE="/dev/console"

read_args
if [ "$debugshell" = y ]; then
    echo -e "Entering initramfs debug shell...\n" >$CONSOLE
    exec sh
fi

# Try to mount the root image read-write and then boot it up.
# This function distinguishes between a read-only image and a read-write image.
# In the former case (typically an iso), it tries to make a union mount if possible.
# In the latter case, the root image could be mounted and then directly booted up.
mount_and_boot() {
    mkdir -p $TSROOT_MOUNT
    run_or_die mount -o ro $TSROOT $TSROOT_MOUNT

    [ -f "$TSROOT_MOUNT/$ROOT_IMAGE" ] ||
        fatal "Couldn't find rootfs image '$ROOT_IMAGE'"

    mkdir -p $ROOT_MOUNT
    mknod /dev/loop0 b 7 0 2>/dev/null
    run_or_die mount -o rw,loop,noatime,nodiratime $TSROOT_MOUNT/$ROOT_IMAGE $ROOT_MOUNT

    if touch $ROOT_MOUNT/bin 2>/dev/null; then
        # The root image is read-write, directly boot it up.
        boot_live_root
    fi

    ro_mount=/run/media/rootfs.ro
    rw_mount=/run/media/rootfs.rw

    mkdir -p $ro_mount $rw_mount
    run_or_die mount -n --move $ROOT_MOUNT $ro_mount

    run_or_die mount -t tmpfs -o rw,noatime,mode=755 tmpfs $rw_mount
    run_or_die mkdir -p $rw_mount/upper $rw_mount/work
    run_or_die mount -t overlay overlay -o "lowerdir=$ro_mount,upperdir=$rw_mount/upper,workdir=$rw_mount/work" $ROOT_MOUNT
    run_or_die mkdir -p $ROOT_MOUNT$ro_mount $ROOT_MOUNT$rw_mount
    run_or_die mount --move $ro_mount ${ROOT_MOUNT}${ro_mount}
    run_or_die mount --move $rw_mount ${ROOT_MOUNT}${rw_mount}

    # boot the image
    boot_live_root
}

mount_and_boot
