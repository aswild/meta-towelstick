DESCRIPTION = "TowelStick packages"
LICENSE = "MIT"

inherit packagegroup

PACKAGES += "${PN}-extra-drivers"
RDEPENDS_${PN}-extra-drivers = " \
    wireguard-module \
    wireguard-tools \
"

PACKAGES += "${PN}-full"
RDEPENDS_${PN}-full = " \
    ${PN}-extra-drivers \
"
