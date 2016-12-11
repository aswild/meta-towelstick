# recipe for clonezilla

DESCRIPTION = "clonezilla"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://doc/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "3.21.13"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/clonezilla-${PV}.tar.bz2"
SRC_URI[md5sum] = "e07b06d52e3ac07832d14c10f23c566c"
SRC_URI[sha256sum] = "d410b393f8f4403fc9fe13609481a7249553dd6d29da309c70132975ac9eb1ba"

RDEPENDS_${PN} = " \
    bash \
    perl \
    drbl \
    ntfsprogs \
    ntfs-3g \
    drbl \
    partclone \
    partimage \
"

PACKAGE_ARCH = "all"

S = "${WORKDIR}/clonezilla-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    oe_runmake DESTDIR=${D} install
}
