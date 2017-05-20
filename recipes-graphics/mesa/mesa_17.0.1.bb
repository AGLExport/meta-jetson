require ${BPN}.inc

LIC_FILES_CHKSUM = "file://docs/license.html;md5=899fbe7e42d494c7c8c159c7001693d5"

SRC_URI = "ftp://ftp.freedesktop.org/pub/mesa/${PV}/mesa-${PV}.tar.xz"

SRC_URI[md5sum] = "6a7e768241846c8c69bbadbf904dcc58"
SRC_URI[sha256sum] = "96fd70ef5f31d276a17e424e7e1bb79447ccbbe822b56844213ef932e7ad1b0c"

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

