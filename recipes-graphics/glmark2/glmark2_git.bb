SUMMARY = "OpenGL (ES) 2.0 benchmark"
DESCRIPTION = "glmark2 is a benchmark for OpenGL (ES) 2.0. \
It uses only the subset of the OpenGL 2.0 API that is compatible with OpenGL ES 2.0."
HOMEPAGE = "https://launchpad.net/glmark2"
BUGTRACKER = "https://bugs.launchpad.net/glmark2"

LICENSE = "GPLv3+ & SGIv1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.SGI;beginline=5;md5=269cdab4af6748677acce51d9aa13552"

DEPENDS = "libpng12 jpeg"

SRC_URI = "git://github.com/glmark2/glmark2.git;protocol=git;branch=master"

SRCREV = "7215c0f337dae0b232535549c37fca441747a891"
S = "${WORKDIR}/git"

inherit waf pkgconfig

PACKAGECONFIG ?= "gl gles2"

PACKAGECONFIG[gl] = ",,virtual/libgl"
PACKAGECONFIG[gles2] = ",,virtual/libgles2"

EXTRA_OECONF += " --with-flavors=wayland-glesv2,wayland-gl"

