# Copied from meta-openembedded/meta-filesystems
# Modifications by Allen Wild

DESCRIPTION = "The NTFS-3G driver is an open source, freely available NTFS driver for Linux with read and write support."
HOMEPAGE = "http://www.ntfs-3g.org/"
DEPENDS = "fuse libgcrypt"
PROVIDES = "ntfsprogs ntfs-3g"
LICENSE = "GPLv2 & LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
                    file://COPYING.LIB;md5=f30a9716ef3762e3467a2f62bf790f0a"

PV = "2016.2.22"
SRC_URI = "http://tuxera.com/opensource/ntfs-3g_ntfsprogs-${PV}.tgz"
S = "${WORKDIR}/ntfs-3g_ntfsprogs-${PV}"

SRC_URI[md5sum] = "ccbe8672d0f757bd0c975b50aa4c512e"
SRC_URI[sha256sum] = "d7b72c05e4b3493e6095be789a760c9f5f2b141812d5b885f3190c98802f1ea0"

inherit autotools pkgconfig

PACKAGECONFIG ??= ""
PACKAGECONFIG[uuid] = "--with-uuid,--without-uuid,util-linux"

# required or it calls ldconfig at install step
EXTRA_OEMAKE = "LDCONFIG=echo"

PACKAGES =+ "ntfs-3g ntfsprogs libntfs-3g"

FILES_ntfs-3g = "${base_sbindir}/*.ntfs-3g ${bindir}/ntfs-3g* ${base_sbindir}/mount.ntfs"
RDEPENDS_ntfs-3g += "fuse"
RRECOMMENDS_ntfs-3g = "util-linux-mount"

FILES_ntfsprogs = "${base_sbindir}/* ${bindir}/* ${sbindir}/*"
FILES_libntfs-3g = "${libdir}/*${SOLIBS}"

do_install_append() {
    # Standard mount will execute the program /sbin/mount.TYPE
    # when called. Add the symbolic to let mount could find ntfs.
    ln -sf mount.ntfs-3g ${D}/${base_sbindir}/mount.ntfs
}

# Satisfy the -dev runtime dependency
ALLOW_EMPTY_${PN} = "1"
