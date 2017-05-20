DESCRIPTION = "NVIDIA Tegra K1/X1 GPU setting script"
LICENSE = "MIT"
SECTION = "base"

LIC_FILES_CHKSUM="file:///${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://tegra-gpu \
           file://78-tegra-gpu.rules \
          "

S="${WORKDIR}"

COMPATIBLE_MACHINE = "jetson-tk1-upstream|jetson-tx1-upstream"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = "${PN}"
FILES_${PN} = "/usr/bin/* /lib/udev/rules.d/*"

do_install () {
    mkdir -p ${D}/usr/bin/
    mkdir -p ${D}/lib/udev/rules.d/

    install -m 555 ${WORKDIR}/tegra-gpu ${D}/usr/bin/
    install -m 644 ${WORKDIR}/78-tegra-gpu.rules ${D}/lib/udev/rules.d/
}
