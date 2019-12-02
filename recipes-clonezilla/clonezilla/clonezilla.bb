# recipe for clonezilla

DESCRIPTION = "clonezilla"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://doc/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "3.35.2"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/${BP}.tar.xz"
SRC_URI[md5sum] = "aaf2f2c009b26cbaf0a2edcdab4f9cfc"
SRC_URI[sha256sum] = "fa9d83d74f22a1993590c85c773ec7c374b9b0d2b1a8658e365171f80bee982e"

RDEPENDS_${PN} = "bash perl drbl dialog"
RRECOMMENDS_${PN} = " \
    mtools \
    ntfs-3g \
    parted \
    partclone \
    partimage \
    pigz \
    sshfs-fuse \
"

inherit allarch

FILES_${PN} += "${datadir}/drbl"

do_install() {
    oe_runmake DESTDIR=${D} install
    rm -rf ${D}/CONTROL ${D}${datadir}/drbl/{pre,post}run ${D}${datadir}/drbl/samples
    chown -R root:root ${D}/*
}
