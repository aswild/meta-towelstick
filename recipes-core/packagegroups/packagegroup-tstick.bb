DESCRIPTION = "TowelStick packages"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = " \
    arch-chroot \
    borgbackup \
    clonezilla \
    cryptsetup \
    gnupg \
    gptfdisk \
    iw \
    kernel-modules \
    linux-firmware \
    lvm2 \
    ntfsprogs \
    openssl \
    openssl-bin \
    terminus-font-consolefonts \
    tree \
    wireguard-tools \
    wpa-oneoff \
    wpa-supplicant \
    xfsdump \
    xfsprogs \
    zstd \
"

RRECOMMENDS_${PN} = "kernel-module-wireguard"

PACKAGES += "${PN}-devel"
RDEPENDS_${PN}-devel = " \
    ${PN} \
    python3 \
    python3-modules \
    python3-pip \
    python3-setuptools \
"
