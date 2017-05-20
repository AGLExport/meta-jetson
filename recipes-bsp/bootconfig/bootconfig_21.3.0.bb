DESCRIPTION = "NVIDIA Linux Bootloader config"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"
SECTION = "base"

SRC_URI = "file://jetson-tk1_extlinux.conf.sdcard"

LIC_FILES_CHKSUM = "file://jetson-tk1_extlinux.conf.sdcard;md5=c2c66ddbca997f158ed005ebbb938dda"

S="${WORKDIR}"

PR = "r1"

COMPATIBLE_MACHINE = "jetson-tk1-upstream"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = "${PN}"
FILES_${PN} = "/boot/extlinux/*"

do_install () {
    mkdir ${D}/boot
    mkdir ${D}/boot/extlinux
    cp ${WORKDIR}/jetson-tk1_extlinux.conf.sdcard ${D}/boot/extlinux/extlinux.conf
}
