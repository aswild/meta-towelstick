SUMMARY = "Utility to backup and restore partitions"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

DEPENDS = "e2fsprogs dosfstools ntfs-3g ncurses"
#RDEPENDS_${PN} = "e2fsprogs libntfs-3g ncurses"

PV = "0.2.91"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/partclone-${PV}.tar.gz"
SRC_URI[md5sum] = "353aafab8ed8ed5b6c2111913aa8905a"
SRC_URI[sha256sum] = "1f73aef9630547eab76717d2a9b87e8f15a79bc5ccebf78df71d9df36d336149"

SRC_URI += "file://0001-fail-mbr.patch"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools gettext pkgconfig

FILESYSTEMS = "extfs xfs fat exfat ntfs"
EXTRA_OECONF = " \
    --enable-ncursesw \
    ${@' '.join('--enable-%s'%fs for fs in d.getVar('FILESYSTEMS').split())} \
"
