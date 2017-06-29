require ${BPN}.inc

LIC_FILES_CHKSUM = "file://docs/license.html;md5=899fbe7e42d494c7c8c159c7001693d5"

SRC_URI = "https://mesa.freedesktop.org/archive/mesa-${PV}.tar.xz"

SRC_URI[md5sum] = "8d088c8f7a099084ac745646ed1ad62b"
SRC_URI[sha256sum] = "f6d75304a229c8d10443e219d6b6c0c342567dbab5a879ebe7cfa3c9139c4492"

#because we cannot rely on the fact that all apps will use pkgconfig,
#make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'true', 'false', d)}; then
        sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if defined(MESA_EGL_NO_X11_HEADERS) || ${@bb.utils.contains('PACKAGECONFIG', 'x11', '0', '1', d)}/' ${D}${includedir}/EGL/eglplatform.h
    fi
}

SRC_URI += " file://0001-gallium-add-Tegra-renderonly-support.patch \
             file://0002-HACK-workaround-to-allow-X-modesetting-to-start.patch \
             file://0003-HACK-disable-check-for-pthread-stubs.patch \
             file://0004-HACK-disable-LLVM-for-cross-builds.patch \
             file://0005-HACK-make-DRI-work-under-X.patch \
             file://0006-loader-Automatic-PRIME-detection.patch \
           "

