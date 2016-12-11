DESCRIPTION = "wildzilla debug and recovery image"

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    kernel-modules \
    linux-firmware \
    gcc \
    make \
    python \
    python3 \
    man \
    ntfs-3g-ntfsprogs \
    gptfdisk \
    vim \
    htop \
    tmux \
    zsh \
    wild-linuxfiles \
    clonezilla \
"

set_default_shell() {
    sed -i '/^root/ s|/bin/sh$|/bin/zsh|' ${IMAGE_ROOTFS}/etc/passwd
}
compress_man_pages() {
    for file in `find ${IMAGE_ROOTFS}${mandir} -type f -name '*.[12345678]'`; do
        if [ ! -f "${file}.gz" ]; then
            gzip -v $file
        fi
    done
}
ROOTFS_POSTPROCESS_COMMAND += "set_default_shell; compress_man_pages;"

inherit_live = "${@'image-live' if 'x86' in d.getVar('MACHINE',True) else ''}"
inherit ${inherit_live} core-image
NOISO = "1"
