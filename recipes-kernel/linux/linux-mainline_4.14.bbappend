# Towelstick kernel customizations

# from meta-wild-common
KERNEL_FEATURES += "squashfs overlayfs"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.14:${THISDIR}/${PN}:"

#DEFCONFIG = "defconfig-ubuntu-artful.scc"
DEFCONFIG = "defconfig-arch.scc"

SRC_URI += " \
    file://${DEFCONFIG} \
    file://udp-tunnel.cfg \
"

# need to override the overrides in the bb
KBUILD_DEFCONFIG_forcevariable = ""
KCONFIG_MODE = "alldefconfig"
