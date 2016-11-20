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
    htop \
    tmux \
    zsh \
    wild-linuxfiles \
"

inherit core-image
