# Towelstick kernel customizations

# from meta-wild-common
KERNEL_FEATURES += "squashfs.scc overlayfs.scc"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LVSHORT}:${THISDIR}/${PN}:"

DEFCONFIG_NAME = "ubuntu"

SRC_URI += " \
    file://defconfig-${DEFCONFIG_NAME}.cfg \
    file://towelstick-${DEFCONFIG_NAME}.cfg \
    file://udp-tunnel.cfg \
"

# need to override the overrides in the bb
KBUILD_DEFCONFIG_forcevariable = ""
KCONFIG_MODE = "alldefconfig"
