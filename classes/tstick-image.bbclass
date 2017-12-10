# Common towelstick live image info

def volume_id(d, split):
    """ construct a volume id (32-bit hex number) from the image name """
    from hashlib import sha1
    h = sha1()
    h.update(d.getVar('IMAGE_NAME').encode('UTF-8'))
    vid = h.hexdigest().upper()[:8]
    if split:
        return '%s-%s'%(vid[:4], vid[4:])
    else:
        return vid

# kernel modules aren't installed by default, and neither is man even when doc-pkgs are enabled
IMAGE_INSTALL_append = " \
    kernel-modules \
    ${@bb.utils.contains('IMAGE_FEATURES', 'doc-pkgs', 'man', '', d)} \
"

IMAGE_FEATURES += "read-only-rootfs"
IMAGE_CLASSES += "image-buildinfo"
IMAGE_CLASSES_remove = "qemuboot"
#IMAGE_FSTYPES = "ext4 squashfs-xz iso hddimg"
IMAGE_FSTYPES = "ext4 squashfs-xz hddimg"

LABELS_LIVE = "boot"
ROOT_LIVE = "tsroot=UUID=${@volume_id(d, True)}"
INITRD_IMAGE_LIVE = "initramfs-tstick"
LIVE_ROOTFS_TYPE = "squashfs-xz"

BOOTIMG_VOLUME_ID = "TOWELSTICK"
HDDIMG_ID = "${@volume_id(d, False)}"

EFI_PROVIDER = "grub-efi"
APPEND = ""
GRUB_SERIAL = ""

inherit core-image
inherit wild-image-postprocess

ROOTFS_POSTPROCESS_COMMAND_remove = "copy_ssh_host_keys;"
