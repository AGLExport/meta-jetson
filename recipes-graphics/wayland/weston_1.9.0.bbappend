FILESEXTRAPATHS_prepend := "${THISDIR}/weston:"

SRC_URI += "file://0001-Update-configure.ac-to-add-check-for-libdrm_tegra.patch \
            file://0002-compositor-drm-Add-new-gbm-struct-to-allow-for-a-sep.patch \
            file://0003-compositor-drm-Add-support-for-Tegra-Jetson-TK1.patch \
            file://0004-Add-glFinish-to-DRM-backend-to-avoid-tearing.patch \
            file://weston.service \
            file://weston-env.sh \
"

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/weston.service ${D}${systemd_unitdir}/system/
    fi

    install -d ${D}/etc/profile.d
    install -m 0755 ${WORKDIR}/weston-env.sh ${D}/etc/profile.d
}

FILES_${PN} += " /etc/profile.d/weston-env.sh"
