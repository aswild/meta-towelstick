DESCRIPTION = "TowelStick packages"
LICENSE = "MIT"

inherit packagegroup

SUBPKGS = " \
    ${PN}-base \
    ${PN}-utils \
    ${PN}-backup \
"
PACKAGES += "${SUBPKGS} ${PN}-full ${PN}-devel"
RDEPENDS_${PN}-full = "${SUBPKGS}"

RDEPENDS_${PN}-base = " \
    linux-firmware \
    terminus-font-consolefonts \
    wireguard-tools \
"
RRECOMMENDS_${PN}-base = "kernel-module-wireguard"

RDEPENDS_${PN}-utils = " \
    arch-chroot \
    cryptsetup \
    gnupg \
    gptfdisk \
    iw \
    openssl \
    openssl-bin \
    ntfsprogs \
    lvm2 \
    tree \
    wpa-supplicant \
    wpa-oneoff \
    xfsdump \
    xfsprogs \
"

RDEPENDS_${PN}-backup = "borgbackup clonezilla"

RDEPENDS_${PN}-devel = " \
    python3 python3-modules python3-pip python3-setuptools \
"
