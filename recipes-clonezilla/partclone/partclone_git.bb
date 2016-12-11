SUMMARY = "Utility to backup and restore partitions"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

DEPENDS = "e2fsprogs dosfstools ncurses"
RDEPENDS_${PN} = "e2fsprogs libntfs-3g ncurses"

PV = "0.2.88"
SRC_URI = "git://github.com/Thomas-Tsai/partclone.git;nobranch=1;tag=${PV}"
SRC_URI += "file://0001-fail-mbr.patch"

S = "${WORKDIR}/git"

inherit autotools gettext pkgconfig

FILESYSTEMS = "extfs xfs fat exfat ntfs"
EXTRA_OECONF = " \
    --enable-ncursesw \
    ${@' '.join('--enable-%s'%fs for fs in d.getVar('FILESYSTEMS',True).split())} \
"
