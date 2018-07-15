SECTION = "kernel"
DESCRIPTION = "Upstream Linux Kernel recipe."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
FILESEXTRAPATHS_prepend_jetson-tk1-upstream := "${THISDIR}/${PN}/k1:"
FILESEXTRAPATHS_prepend_jetson-tx1-upstream := "${THISDIR}/${PN}/x1:"

COMPATIBLE_MACHINE = "jetson-tk1-upstream|jetson-tx1-upstream"

#DEFAULT_PREFERENCE = "-1"

FREV = "2567e092339cd3403d697dc2e0967c31b7acb989"

PR = "r0"
SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
           https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/snapshot/linux-firmware-${FREV}.tar.gz;name=firmware \
           file://defconfig \
"

S = "${WORKDIR}/linux-${PV}"

# Patches
SRC_URI_append_jetson-tk1-upstream = " \
        file://0001-Enable-GPU-for-jetson-TK1-board.patch \
        "

SRC_URI_append_jetson-tx1-upstream = " \
        file://csite_clock.patch \
        file://0001-arm64-enable-Tegra-DRM.patch \
        file://0002-arm64-enable-Tegra-X1-ethernet-controller.patch \
        file://0003-arm64-enable-Tegra-PWM-MC-IOMMU.patch \
        file://0007-arm64-tegra-Add-VDD_GPU-regulator-to-Jetson-TX1.patch \
        file://0008-arm64-tegra-Enable-all-cores-on-Jetson-TX1.patch \
        file://0009-HACK-Enable-GM20B.patch \
        file://0010-disable-first-DC.patch \
        file://0011-drm-tegra-add-tiling-FB-modifiers.patch \
        file://0004-enable-tegra-staging.patch \
        file://usb_extra_firmware_tx1.patch \
        "

# Kernel Config
SRC_URI_append_jetson-tk1-upstream = " \
        file://tk1.cfg \
        "


SRC_URI[md5sum] = "bfbc0300cfac4b00fd7cee1d95082d92"
SRC_URI[sha256sum] = "a85f2572f97dc551f4a159d0c0858e6f40b925afd2d14a0aa25ee9238da80bbf"
SRC_URI[firmware.md5sum] = "fc1a0eb15d9d3230d269b0824c8bedaf"
SRC_URI[firmware.sha256sum] = "0f6d3e87b1ea674cc5ee3688e8cf1c3dc2e3a92a226db038b8651c2558d928f4"

KERNEL_DEFCONFIG_jetson-tk1-upstream = "tegra_defconfig"
KERNEL_DEFCONFIG_jetson-tx1-upstream = "defconfig"
KERNEL_DEVICETREE_jetson-tk1-upstream = "tegra124-jetson-tk1.dtb"
KERNEL_DEVICETREE_jetson-tx1-upstream = "nvidia/tegra210-p2371-2180.dtb"

KERNEL_IMAGETYPE_jetson-tk1-upstream = "zImage"
KERNEL_IMAGETYPE_jetson-tx1-upstream = "Image"

#KBUILD_DEFCONFIG ?= "tegra_defconfig"

#do_kernel_metadata_prepend() {
#        install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig
#}

do_configure_append_jetson-tx1-upstream() {
        mkdir -p ${S}/nvidia/tegra210
        install -m 0644 ${WORKDIR}/linux-firmware-${FREV}/nvidia/tegra210/xusb.bin ${S}/nvidia/tegra210/
}

do_configure_append_jetson-tk1-upstream() {
        mkdir -p ${S}/nvidia/tegra124
        install -m 0644 ${WORKDIR}/linux-firmware-${FREV}/nvidia/tegra124/xusb.bin ${S}/nvidia/tegra124/
}
