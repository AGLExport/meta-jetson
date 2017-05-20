SUMMARY = "Demo application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://kmscube.c;beginline=1;endline=23;md5=8b309d4ee67b7315ff7381270dd631fb"

DEPENDS = "libdrm virtual/egl"

inherit autotools pkgconfig


SRC_URI = "git://github.com/Gnurou/kmscube.git;protocol=git;branch=gk20a \
           "

S = "${WORKDIR}/git"

SRCREV = "5bccc25921d669f444d470d93a0f6dfdbd5a5a2d"

INSANE_SKIP_kmscube += "dev-deps"

FILESEXTRAPATHS_prepend := "${THISDIR}:"
