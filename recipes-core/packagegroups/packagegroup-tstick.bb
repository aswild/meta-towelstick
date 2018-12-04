DESCRIPTION = "TowelStick packages"
LICENSE = "MIT"

inherit packagegroup

SUBPKGS = " \
    ${PN}-extra-drivers \
    ${PN}-utils \
    ${PN}-clonezilla \
"
PACKAGES += "${SUBPKGS} ${PN}-full"
RDEPENDS_${PN}-full = "${SUBPKGS}"

RDEPENDS_${PN}-extra-drivers = " \
    linux-firmware \
    wireguard-tools \
"
RRECOMMENDS_${PN}-extra-drivers = "kernel-module-wireguard"

RDEPENDS_${PN}-utils = " \
    arch-chroot \
    cryptsetup \
    gptfdisk \
    iw \
    ntfsprogs \
    lvm2 \
    tree \
    wpa-supplicant \
    wpa-oneoff \
    xfsdump \
    xfsprogs \
"

RDEPENDS_${PN}-clonezilla = "clonezilla"
