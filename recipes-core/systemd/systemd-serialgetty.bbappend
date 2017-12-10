# Don't try to auto-start serialgetty; it spams the journal with restart messages
# and most PCs don't have a serial port we'd use anyway
do_install_append() {
    if [ -d  ${D}${sysconfdir}/systemd/system/getty.target.wants ]; then
        find ${D}${sysconfdir}/systemd/system/getty.target.wants -name 'serial-getty*.service' \
             -exec rm -v {} \;
    fi
}
