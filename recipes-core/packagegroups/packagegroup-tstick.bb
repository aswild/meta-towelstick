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
    wireguard-module \
    wireguard-tools \
"

RDEPENDS_${PN}-utils = " \
    cryptsetup \
    gptfdisk \
    iw \
    ntfsprogs \
    lvm2 \
    wireless-tools \
    wpa-supplicant \
    wpa-oneoff \
    xfsdump \
    xfsprogs \
"

RDEPENDS_${PN}-clonezilla = "clonezilla"
