SECTION = "kernel"
DESCRIPTION = "Upstream Linux Kernel recipe."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require linux.inc
require recipes-kernel/linux/linux-dtb.inc

COMPATIBLE_MACHINE = "jetson-tk1-upstream|jetson-tx1-upstream"


DEFAULT_PREFERENCE = "-1"

S = "${WORKDIR}/linux-4.9.35"

PR = "r35"
SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-4.9.35.tar.xz;name=linux \
           git://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git;name=firmware;rev=8d1fd61a3723ab8cb6b7bfeb8be38e16282cc1ed \
"

SRC_URI_append_jetson-tk1-upstream = " \
        file://linux4.3_tegrak1_gpu_hdmi.patch \
        file://0006-arm-tegra-add-SECCOMP-support-for-systemd-231.patch \
        file://0011-drm-tegra-add-tiling-FB-modifiers.patch \
        file://tk1_enable_tegra_staging.patch \
        file://usb_extra_firmware_tk1.patch \
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

SRC_URI[linux.md5sum] = "9b72bca09f8192ff645b354a4f7960ea"
SRC_URI[linux.sha256sum] = "3491234ed79f3e4a425457edf96447773be677521932a3e5bafb79834c5e7436"

KERNEL_DEFCONFIG_jetson-tk1-upstream = "tegra_defconfig"
KERNEL_DEFCONFIG_jetson-tx1-upstream = "defconfig"
KERNEL_DEVICETREE_jetson-tk1-upstream = "tegra124-jetson-tk1.dtb"
KERNEL_DEVICETREE_jetson-tx1-upstream = "nvidia/tegra210-p2371-2180.dtb"

KERNEL_IMAGETYPE_jetson-tk1-upstream = "zImage"
KERNEL_IMAGETYPE_jetson-tx1-upstream = "Image"

do_configure_prepend() {
        install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
}

do_configure_append_jetson-tx1-upstream() {
        mkdir -p ${S}/nvidia/tegra210
        install -m 0644 ${WORKDIR}/git/nvidia/tegra210/xusb.bin ${S}/nvidia/tegra210/
}

do_configure_append_jetson-tk1-upstream() {
        mkdir -p ${S}/nvidia/tegra124
        install -m 0644 ${WORKDIR}/git/nvidia/tegra124/xusb.bin ${S}/nvidia/tegra124/
}
