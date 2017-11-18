# initramfs for live images, based on core-image-minimal-initramfs

KERNEL_MODULES = " \
    kernel-module-nouveau \
"

PACKAGE_INSTALL = " \
    initramfs-live-boot \
    udev base-passwd \
    ${VIRTUAL-RUNTIME_base-utils} \
    ${ROOTFS_BOOTSTRAP_INSTALL} \
    ${KERNEL_MODULES} \
"

IMAGE_FEATURES = ""
#IMAGE_CLASSES_forcevariable = ""

export IMAGE_BASENAME = "initramfs-tstick"
IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
inherit core-image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

BAD_RECOMMENDATIONS += "busybox-syslog"
