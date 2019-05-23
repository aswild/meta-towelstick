SUMMARY = "Utility to backup and restore partitions"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "0.3.12"
SRCREV = "5e00059f07e2303e44a41edef01b8bddeac4d720"
SRC_URI = " \
    git://github.com/Thomas-Tsai/partclone \
    file://0001-fail-mbr.patch \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep gettext pkgconfig
DEPENDS = "ncurses openssl"
EXTRA_OECONF = "--enable-ncursesw"

PACKAGECONFIG ?= "xfs extfs hfsp fat exfat ntfs btrfs minix f2fs"
PACKAGECONFIG[xfs]   = "--enable-xfs,--disable-xfs"
PACKAGECONFIG[extfs] = "--enable-extfs,--disable-extfs,e2fsprogs"
PACKAGECONFIG[hfsp]  = "--enable-hfsp,--disable-hfsp"
PACKAGECONFIG[fat]   = "--enable-fat,--disable-fat"
PACKAGECONFIG[exfat] = "--enable-exfat,--disable-exfat"
PACKAGECONFIG[ntfs]  = "--enable-ntfs,--disable-ntfs,ntfs-3g"
PACKAGECONFIG[btrfs] = "--enable-btrfs,--disable-btrfs"
PACKAGECONFIG[minix] = "--enable-minix,--disable-minix"
PACKAGECONFIG[f2fs]  = "--enable-f2fs,--disable-f2fs"
