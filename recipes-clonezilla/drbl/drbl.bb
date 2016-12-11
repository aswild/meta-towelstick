# Recipe for clonezilla

DESCRIPTION = "clonezilla"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://doc/GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "2.20.11"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/drbl-${PV}.tar.bz2"
SRC_URI[md5sum] = "e8bf0b91c5d2a319371e6ef4142c61d8"
SRC_URI[sha256sum] = "fc13bcdd668cad4afc8648a8174d67abb4522878b40572fcb81694592ed27000"

# Use "cp -dr" instead of "cp -a" to avoid host-user-contaminated
SRC_URI += "file://0001-makefile-fix.patch"

PACKAGE_ARCH = "all"
RDEPENDS_${PN} = " \
    bash \
    perl \
"

FILES_${PN} = " \
    /usr/sbin/* \
    /usr/bin/* \
    /usr/share/drbl/* \
    /etc/drbl/* \
"
PACKAGES += "${PN}-gdm"
FILES_${PN}-gdm = "/usr/share/gdm/*"

S = "${WORKDIR}/drbl-${PV}"

do_configure[noexec] = "1"

do_compile() {
    oe_runmake all
}

do_install() {
    oe_runmake DESTDIR=${D} install
}
