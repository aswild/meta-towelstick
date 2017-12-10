# autologin as root
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://autologin.conf"

do_install_append() {
    install -Dm644 ${WORKDIR}/autologin.conf ${D}${sysconfdir}/systemd/system/getty@tty1.service.d/autologin.conf
}
