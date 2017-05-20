SUMMARY = "mesa nouveau append recipe"

PACKAGECONFIG_append = " gallium "

DIR3_FEATURES = "dri3proto presentproto "
DIR3_FEATURES += " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'libxshmfence', '', d)} "

PACKAGECONFIG[dri3] = "--enable-dri3, --disable-dri3, ${DIR3_FEATURES} "

GALLIUMDRIVERS = "nouveau"
DRIDRIVERS_append = ",nouveau"

