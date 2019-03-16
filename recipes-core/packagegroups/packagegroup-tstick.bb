DESCRIPTION = "TowelStick packages"
LICENSE = "MIT"

inherit packagegroup

SUBPKGS = " \
    ${PN}-extra-drivers \
    ${PN}-utils \
    ${PN}-clonezilla \
"
PACKAGES += "${SUBPKGS} ${PN}-full ${PN}-devel"
RDEPENDS_${PN}-full = "${SUBPKGS}"

RDEPENDS_${PN}-extra-drivers = " \
    linux-firmware \
    wireguard-tools \
"
RRECOMMENDS_${PN}-extra-drivers = "kernel-module-wireguard"

RDEPENDS_${PN}-utils = " \
    arch-chroot \
    cryptsetup \
    gnupg \
    gptfdisk \
    iw \
    openssl \
    ntfsprogs \
    lvm2 \
    tree \
    wpa-supplicant \
    wpa-oneoff \
    xfsdump \
    xfsprogs \
"

RDEPENDS_${PN}-clonezilla = "clonezilla"

RDEPENDS_${PN}-devel = " \
    python3 python3-modules python3-pip python3-setuptools \
"
