# cpio and tar conflict because of rmt.8 when using doc-pkgs in IMAGE_FEATURES
# so remove the cpio version
do_install_append() {
    rm -f ${D}${mandir}/man8/rmt.8
}
