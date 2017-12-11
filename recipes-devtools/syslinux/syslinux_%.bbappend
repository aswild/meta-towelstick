# gold linker breaks syslinux, so always use BFD
LD = "${HOST_PREFIX}ld.bfd${TOOLCHAIN_OPTIONS} ${HOST_LD_ARCH}"
