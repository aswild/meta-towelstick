DESCRIPTION = "TowelStick packages"
LICENSE = "MIT"

inherit packagegroup

SUBPKGS = " \
    ${PN}-extra-drivers \
    ${PN}-utils \
"
PACKAGES += "${SUBPKGS} ${PN}-full"
RDEPENDS_${PN}-full = "${SUBPKGS}"

RDEPENDS_${PN}-extra-drivers = " \
    wireguard-module \
    wireguard-tools \
"

RDEPENDS_${PN}-utils = " \
    gptfdisk \
    xfsdump \
    xfsprogs \
"
