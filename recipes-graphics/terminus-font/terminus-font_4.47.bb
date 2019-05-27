# based on meta-oe recipes-graphics/terminus-font/terminus-font_4.38.bb
SUMMARY = "Terminus fonts packages (console and X11)"
DESCRIPTION = "Terminus Font is a clean, fixed width bitmap font, designed for \
               long (8 and more hours per day) work with computers."
HOMEPAGE = "http://terminus-font.sourceforge.net/"
AUTHOR = "Dimitar Zhekov"
SECTION = "fonts"

LICENSE = "OFL-1.1"
LIC_FILES_CHKSUM = "file://OFL.TXT;md5=1a547c1433ef32b02465ee623544d6b1"

DEPENDS = "gzip-native"

SRC_URI = "${SOURCEFORGE_MIRROR}/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[md5sum] = "d95f0914e31b838bd54edf8389fdc6cd"
SRC_URI[sha256sum] = "0f1b205888e4e26a94878f746b8566a65c3e3742b33cf9a4e6517646d5651297"

inherit allarch fontcache

PACKAGECONFIG ?= "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}"
PACKAGECONFIG[x11] = ",,bdftopcf-native"

# Don't use font cache mecanism for console packages
FONT_PACKAGES = "${@bb.utils.contains('PACKAGECONFIG', 'x11', '${PN}-pcf', '', d)}"

# Hand made configure script. Don't need oe_runconf
do_configure() {
    chmod +x ${S}/configure
    ${S}/configure --prefix=${prefix} \
                   --psfdir=${datadir}/consolefonts \
                   --x11dir=${datadir}/fonts/terminus
}

do_compile() {
    oe_runmake DESTDIR=${D} psf ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'pcf', '', d)}
}

do_install() {
    oe_runmake DESTDIR=${D} install-psf ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'install-pcf', '', d)}
}

PACKAGES += "${PN}-consolefonts ${PN}-pcf"
FILES_${PN}-consolefonts = "${datadir}/consolefonts"
FILES_${PN}-pcf = "${datadir}/fonts/terminus"
