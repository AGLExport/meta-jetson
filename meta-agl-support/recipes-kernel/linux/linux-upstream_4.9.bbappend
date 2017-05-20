FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

SRC_URI += " file://drm.cfg \
             file://usb.cfg \
             file://usbtouch.cfg \
             file://usbaudio.cfg \
             file://most.cfg \
           "

SRC_URI_append_smack = "\
	file://0004-Smack-Assign-smack_known_web-label-for-kernel-thread.patch \
"

KERNEL_CONFIG_FRAGMENTS_append = " \
             ${WORKDIR}/drm.cfg ${WORKDIR}/usbtouch.cfg \
             ${WORKDIR}/usbaudio.cfg ${WORKDIR}/most.cfg \
"
