# Common towelstick live image info

def volume_id(d):
    """ construct a volume id (32-bit hex number) from the image name """
    from hashlib import sha1
    h = sha1()
    h.update(d.getVar('IMAGE_NAME').encode('UTF-8'))
    return h.hexdigest().upper()[:8]

# kernel modules aren't installed by default, and neither is man even when doc-pkgs are enabled
IMAGE_INSTALL_append = " \
    packagegroup-base \
    kernel-modules \
    ${@bb.utils.contains('IMAGE_FEATURES', 'doc-pkgs', 'man', '', d)} \
"

inherit squashfs-zstd

IMAGE_FEATURES += ""
IMAGE_CLASSES += "image-buildinfo"
IMAGE_CLASSES_remove = "qemuboot"
IMAGE_FSTYPES = "squashfs-zstd iso hddimg"

# volume label, used for mounting the USB/ISO rootfs
# Can be up to 11 characters, per FAT limitation
# this variable is used by mkdosfs and mkisofs commands
BOOTIMG_VOLUME_ID = "TSTICK-${@volume_id(d)[4:]}"

# FAT filesystem UUID
HDDIMG_ID = "${@volume_id(d)}"

# ends up in kernel cmdline, used by init-live.sh to mount the /boot filesystem
ROOT_LIVE = "tsroot=LABEL=${BOOTIMG_VOLUME_ID}"

LABELS_LIVE = "boot"
INITRD_IMAGE_LIVE = "initramfs-tstick"
LIVE_ROOTFS_TYPE = "squashfs-zstd"

EFI_PROVIDER = "grub-efi"
APPEND = ""
GRUB_SERIAL = ""

inherit wild-image

ROOTFS_POSTPROCESS_COMMAND_remove = "copy_ssh_host_keys;"

inherit qemuboot
QB_DEFAULT_FSTYPE = "hddimg"

python do_write_qemuboot_conf_append() {
    # hack to get qemu to boot in raw disk mode, i.e. without a kernel
    # qemuboot is set to the conf file that was just written, rewrite it
    # while deleting all the lines with 'kernel' in them
    import re
    r = re.compile(r'\w*kernel\w*')
    with open(qemuboot, 'r') as fp:
        conf = [line for line in fp if r.match(line) is None]
    with open(qemuboot, 'w') as fp:
        print(' '.join(conf), file=fp)
}
