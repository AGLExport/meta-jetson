SUMMARY = "mesa nouveau append recipe"

PACKAGECONFIG_append = " gallium "

DIR3_FEATURES = "dri3proto presentproto "
DIR3_FEATURES += " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'libxshmfence', '', d)} "

PACKAGECONFIG[dri3] = "--enable-dri3, --disable-dri3, ${DIR3_FEATURES} "

GALLIUMDRIVERS = "nouveau,tegra"
DRIDRIVERS_append = ",nouveau"

SRC_URI += " file://0001-gallium-add-Tegra-renderonly-support.patch \
             file://0002-HACK-workaround-to-allow-X-modesetting-to-start.patch \
             file://0003-HACK-disable-check-for-pthread-stubs.patch \
             file://0004-HACK-disable-LLVM-for-cross-builds.patch \
             file://0005-HACK-make-DRI-work-under-X.patch \
             file://0006-loader-Automatic-PRIME-detection.patch \
           "
