require core-image-tstick.bb

IMAGE_FEATURES += "dev-pkgs doc-pkgs tools-sdk"

IMAGE_INSTALL += " \
    packagegroup-core-buildessential \
    packagegroup-tstick-devel \
"

symlink_pyth() {
    if [ ! -e ${IMAGE_ROOTFS}${bindir}/python ] && [ -e ${IMAGE_ROOTFS}${bindir}/python3 ]; then
        ln -s python3 ${IMAGE_ROOTFS}${bindir}/python
    fi
}
ROOTFS_POSTPROCESS_COMMAND += "symlink_pyth;"

# prelink is broken for the dev image right now
# prelink: ../../git/src/arch-x86_64.c:421: x86_64_arch_prelink: Assertion `i < dso->ehdr.e_shnum' failed.
IMAGE_PREPROCESS_COMMAND_remove = "prelink_setup; prelink_image;"
