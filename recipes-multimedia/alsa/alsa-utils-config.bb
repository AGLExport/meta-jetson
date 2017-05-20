SUMMARY = "ALSA sound configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM="file:///${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} = "alsa-utils"

FILESEXTRAPATHS_prepend := ":${THISDIR}/alsa-utils-config:"

SRC_URI = "file://asound.state \
          "

do_install() {
       cp ${WORKDIR}/asound.state* ${S}/asound.state
       install -d ${D}/${localstatedir}/lib/alsa
       install -m 0644 asound.state ${D}/${localstatedir}/lib/alsa
}

FILES_${PN} += "${localstatedir}"
