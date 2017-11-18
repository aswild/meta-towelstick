require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL += "kernel-modules"

IMAGE_FSTYPES += "iso"
NOISO = "0"
NOHDD = "0"
LIVE_ROOTFS_TYPE = "squashfs-xz"
ROOT_LIVE = ""
LABELS_LIVE = "boot"
MKDOSFS_EXTRAOPTS = "-n TOWELSTICK"

EFI_PROVIDER = "grub-efi"
APPEND = ""
GRUB_SERIAL = ""

INITRD_IMAGE_LIVE = "initramfs-tstick"
inherit image-live
