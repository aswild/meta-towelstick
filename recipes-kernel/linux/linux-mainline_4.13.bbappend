# Towelstick kernel customizations

# from meta-wild-common
KERNEL_FEATURES += "squashfs overlayfs"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.13:"
SRC_URI += " \
    file://defconfig-arch-x86-64.cfg \
    file://no-module-compress.cfg \
    file://udp-tunnel.cfg \
"

# need to override the overrides in the bb
KBUILD_DEFCONFIG_forcevariable = ""
KCONFIG_MODE = "allnoconfig"
