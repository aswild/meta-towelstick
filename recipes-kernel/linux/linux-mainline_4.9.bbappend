# Towelstick kernel customizations

# from meta-wild-common
KERNEL_FEATURES += "squashfs overlayfs"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.9:${THISDIR}/${PN}:"

DEFCONFIG = "defconfig-ubuntu.cfg ubuntu-to-yocto.cfg"
#DEFCONFIG = "defconfig-arch.scc"

SRC_URI += " \
    ${@' '.join(['file://' + f for f in d.getVar('DEFCONFIG').split()])} \
    file://udp-tunnel.cfg \
"

# need to override the overrides in the bb
KBUILD_DEFCONFIG_forcevariable = ""
KCONFIG_MODE = "alldefconfig"
