# create /etc/wpa_supplicant/ for putting config files in
do_install_append() {
    install -d ${D}${sysconfdir}/wpa_supplicant
}
