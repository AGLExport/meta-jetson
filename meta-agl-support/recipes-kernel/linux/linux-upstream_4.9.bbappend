FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

require recipes-kernel/linux/linux-agl.inc
require recipes-kernel/linux/linux-agl-4.9.inc

SRC_URI += " file://usb-tegra.cfg \
             file://usbtouch.cfg \
           "
KERNEL_CONFIG_FRAGMENTS_append = " \
             ${WORKDIR}/usb-tegra.cfg ${WORKDIR}/usbtouch.cfg \
"
