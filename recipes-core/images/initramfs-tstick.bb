# initramfs for live images, based on core-image-minimal-initramfs
LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
inherit core-image

UTILS = " \
    util-linux-lsblk \
    usbutils \
    pciutils \
"

PACKAGE_INSTALL = " \
    initramfs-live-boot \
    initramfs-reboot \
    packagegroup-initramfs-tstick-modules \
    udev base-passwd \
    busybox \
    ${ROOTFS_BOOTSTRAP_INSTALL} \
    ${UTILS} \
"

IMAGE_FEATURES = ""
#IMAGE_CLASSES_forcevariable = ""

export IMAGE_BASENAME = "initramfs-tstick"
IMAGE_LINGUAS = ""

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

BAD_RECOMMENDATIONS += "busybox-syslog"

tstick_init_postprocess() {
    # we don't need the kernel image embedded in the initramfs, that's silly
    rm -rvf ${IMAGE_ROOTFS}/boot

    # remove the automount rules/script from udev-extraconf
    rm -vf ${IMAGE_ROOTFS}${sysconfdir}/udev/rules.d/automount.rules
    rm -vf ${IMAGE_ROOTFS}${sysconfdir}/udev/scripts/mount.sh
}
ROOTFS_POSTPROCESS_COMMAND_append = "tstick_init_postprocess;"
