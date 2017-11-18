inherit tstick-image

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    packagegroup-wild-core \
    packagegroup-wild-utils \
    packagegroup-wild-network-utils \
    ${CORE_IMAGE_EXTRA_INSTALL} \
"
