# Recipe for clonezilla

SUMMARY = "DRBL: Diskless Remote Boot in Linux"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://doc/GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "2.25.10"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/drbl-${PV}.tar.xz"
SRC_URI[md5sum] = "9bebdca9b20b0309d6b7c4b2378e11d6"
SRC_URI[sha256sum] = "aa5c5d5fabb0a0e6cd88c6a8ea07ff3b3655d56dc9898370834d6f3c261abc0e"

# Use "cp -dr" instead of "cp -a" to avoid host-user-contaminated
SRC_URI += "file://drbl-makefile.patch"

PACKAGE_ARCH = "all"
RDEPENDS_${PN} = "bash perl"

PACKAGES =+ "${PN}-gdm"
FILES_${PN}-gdm = "/usr/share/gdm/*"
FILES_${PN} += " \
    /etc/drbl/* \
"

S = "${WORKDIR}/drbl-${PV}"

do_install() {
    oe_runmake DESTDIR=${D} install
    rm -rf ${D}${datadir}/drbl/{pre,post}run
}
