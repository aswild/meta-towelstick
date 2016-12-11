# partimage recipe

DESCRIPTION = "Partimage"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=cbbd794e2a0a289b9dfcc9f513d1996e"

PV = "0.6.9"
SRC_URI = "${SOURCEFORGE_MIRROR}/partimage/stable/${PV}/partimage-${PV}.tar.bz2"
SRC_URI[md5sum] = "1bc046fd915c5debbafc85729464e513"
SRC_URI[sha256sum] = "753a6c81f4be18033faed365320dc540fe5e58183eaadcd7a5b69b096fec6635"

# patches from Arch Linux package
SRC_URI += " \
    file://0001-partimage-0.6.9-zlib-1.2.6.patch \
    file://0002-use-SSLv3-by-default.patch \
"

DEPENDS = "libnewt zlib bzip2 openssl"

S = "${WORKDIR}/partimage-${PV}"

inherit autotools pkgconfig gettext

EXTRA_OECONF = "--with-ssl-headers=${STAGING_DIR_TARGET}/usr/include/openssl"
