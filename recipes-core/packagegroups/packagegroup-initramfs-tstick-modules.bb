# use a packagegroup to wrap kernel-module-* packages with RRECOMMENDS instead
# of directly in PACKAGE_INSTALL this prevents errors if these drivers become
# built-in to the kernel and no longer exist as packages

inherit packagegroup

KERNEL_MODULES = " \
    amdgpu \
    i915 \
    isofs \
    nouveau \
    virtio-scsi \
"

RRECOMMENDS_${PN} = "${@' '.join(['kernel-module-'+m for m in d.getVar('KERNEL_MODULES').split()])}"
