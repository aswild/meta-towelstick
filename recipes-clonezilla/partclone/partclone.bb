SUMMARY = "Utility to backup and restore partitions"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "e2fsprogs dosfstools ntfs-3g ncurses"

PV = "0.3.11"
SRC_URI = "https://partclone.nchc.org.tw/download/unstable/${BP}.tar.gz"
SRC_URI[md5sum] = "caede528e8a240aef9a1f12e714989c3"
SRC_URI[sha256sum] = "d7df69c53ccf001cd734722f9acfc007f054db1559232155a1cc6de188d9c898"

SRC_URI += "file://0001-fail-mbr.patch"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools-brokensep gettext pkgconfig

FILESYSTEMS = "extfs xfs fat exfat ntfs"
EXTRA_OECONF = " \
    --enable-ncursesw \
    ${@' '.join('--enable-%s'%fs for fs in d.getVar('FILESYSTEMS').split())} \
"
