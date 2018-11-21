# recipe for clonezilla

DESCRIPTION = "clonezilla"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://doc/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "3.27.16"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/clonezilla-${PV}.tar.xz"
SRC_URI[md5sum] = "4beae63adc5d75632b2b2a07f0f095bc"
SRC_URI[sha256sum] = "3e5f8d8c47d860361c9ed4a268351c40c15fa565fcbe90540bcab5e3cfa7ed3d"

SRC_URI += "file://clonezilla-makefile.patch"

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

S = "${WORKDIR}/clonezilla-${PV}"

FILES_${PN} += "${datadir}/drbl"

do_install() {
    oe_runmake DESTDIR=${D} install
    rm -rf ${D}/CONTROL ${D}${datadir}/drbl/{pre,post}run ${D}${datadir}/drbl/samples
}
