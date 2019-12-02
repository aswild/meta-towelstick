# Recipe for clonezilla

SUMMARY = "DRBL: Diskless Remote Boot in Linux"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://doc/GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "2.30.5"
SRC_URI = "http://free.nchc.org.tw/drbl-core/src/stable/${BP}.tar.xz"
SRC_URI[md5sum] = "5d649c68bae688db122d1e7ac25b111c"
SRC_URI[sha256sum] = "a5856b6effef9df2488c2e84d47ceac3440fef4f8fd4071dae84fadc78417e30"

inherit allarch

RDEPENDS_${PN} = "bash perl"

PACKAGES =+ "${PN}-gdm-theme"
FILES_${PN}-gdm-theme = "/usr/share/gdm"

do_install() {
    oe_runmake DESTDIR=${D} install
    rm -rf ${D}${datadir}/drbl/{pre,post}run
    chown -R root:root ${D}/*
}
