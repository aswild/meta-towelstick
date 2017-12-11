# autologin as root
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://autologin.conf \
    file://ethernet-dhcp.network \
"

do_install_append() {
    install -Dm644 ${WORKDIR}/autologin.conf ${D}${sysconfdir}/systemd/system/getty@tty1.service.d/autologin.conf
    install -Dm644 ${WORKDIR}/ethernet-dhcp.network ${D}${systemd_unitdir}/network/ethernet-dhcp.network
}
