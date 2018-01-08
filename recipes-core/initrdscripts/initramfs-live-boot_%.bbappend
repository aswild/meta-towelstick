# overlay my copy of init-live.sh
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_prepend() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', 'true', 'false', d)}; then
        path=/usr/sbin:/usr/bin
    else
        path=/sbin:/bin:/usr/sbin:/usr/bin
    fi
    sed -i "s|@PATH@|$path|" ${WORKDIR}/init-live.sh
}
