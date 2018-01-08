DESCRIPTION = "reboot/halt/poweroff program suitable for use in an initramfs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://reboot.cpp;endline=24;md5=842e136002fa24f0096b9d1030a4575b"

PV = "1.0"
SRC_URI = "file://reboot.cpp"
S = "${WORKDIR}"

inherit update-alternatives
ALTERNATIVE_${PN} = "reboot poweroff halt"
ALTERNATIVE_LINK_NAME[reboot] = "${base_sbindir}/reboot"
ALTERNATIVE_LINK_NAME[poweroff] = "${base_sbindir}/poweroff"
ALTERNATIVE_LINK_NAME[halt] = "${base_sbindir}/halt"
ALTERNATIVE_TARGET = "${base_sbindir}/reboot.${BPN}"
ALTERNATIVE_PRIORITY = "999"

CXXFLAGS += "-Wall -Werror"
do_configure[noexec] = "1"

do_compile() {
    ${CXX} ${CXXFLAGS} ${LDFLAGS} -o reboot reboot.cpp
}

do_install() {
    install -Dm755 ${WORKDIR}/reboot ${D}${base_sbindir}/reboot.${BPN}
    #ln -s reboot ${D}${base_sbindir}/poweroff
    #ln -s reboot ${D}${base_sbindir}/halt
}
