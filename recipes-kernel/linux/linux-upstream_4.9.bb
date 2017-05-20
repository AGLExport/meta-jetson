SECTION = "kernel"
DESCRIPTION = "Upstream Linux Kernel recipe."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KERNEL_IMAGETYPE = "zImage"

inherit kernel

require linux.inc
require recipes-kernel/linux/linux-dtb.inc

DEFAULT_PREFERENCE = "-1"

S = "${WORKDIR}/linux-4.9.8"

PR = "r8"
SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-4.9.8.tar.xz \
           file://linux4.3_tegrak1_gpu_hdmi.patch \
           file://xhci_tegra_config.patch \
           file://defconfig"

SRC_URI[md5sum] = "7b56bb1196642f0f6625906c6720a6b1"
SRC_URI[sha256sum] = "150bb7f2dd4849b5d21b8ccd8d05294a48229e1fcb93a22e7b806a79ec0b0e45"

KERNEL_DEFCONFIG = "tegra_defconfig"
KERNEL_DEVICETREE = "tegra124-jetson-tk1.dtb"

do_configure_prepend() {
        install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
}
