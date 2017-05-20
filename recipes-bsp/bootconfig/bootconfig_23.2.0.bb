DESCRIPTION = "NVIDIA Linux Bootloader config"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"
SECTION = "base"

FILESEXTRAPATHS_prepend_jetson-tx1-upstream := "${THISDIR}/${PN}/tx1:"

SRC_URI = "file://extlinux.conf.sdcard"

LIC_FILES_CHKSUM = "file://extlinux.conf.sdcard;md5=4f527e6f7f36b3a0597f2ae1de5ea5d0"

S="${WORKDIR}"

PR = "r0" 

COMPATIBLE_MACHINE = "jetson-tx1-upstream"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = "${PN}"
FILES_${PN} = "/boot/extlinux/*"

do_install () {
    mkdir ${D}/boot
    mkdir ${D}/boot/extlinux
    cp ${WORKDIR}/extlinux.conf.sdcard ${D}/boot/extlinux/extlinux.conf
}
