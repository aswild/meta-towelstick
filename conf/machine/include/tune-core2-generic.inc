# Like oe-core's core2-64 tune but with '-march=core2 -mtune=generic' rather than '-march=core2 -mtune=core2'

TUNEVALID[core2-generic] = "Enable core2 architecture with generic optimizations"
TUNE_CCARGS .= "${@bb.utils.contains('TUNE_FEATURES', 'core2-generic', ' -march=core2 -mtune=generic -msse3 -mfpmath=sse', '', d)}"

AVAILTUNES += "core2-64-generic"
TUNE_FEATURES_tune-core2-64-generic = "${TUNE_FEATURES_tune-x86-64} core2-generic"
BASE_LIB_tune-core2-64-generic = "lib64"
TUNE_PKGARCH_tune-core2-64-generic = "core2-64"
PACKAGE_EXTRA_ARCHS_tune-core2-64-generic = "${PACKAGE_EXTRA_ARCHS_tune-x86-64} core2-64"
QEMU_EXTRAOPTIONS_core2-64-generic = " -cpu core2duo"
