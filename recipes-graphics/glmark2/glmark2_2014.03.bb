SUMMARY = "OpenGL (ES) 2.0 benchmark"
DESCRIPTION = "glmark2 is a benchmark for OpenGL (ES) 2.0. \
It uses only the subset of the OpenGL 2.0 API that is compatible with OpenGL ES 2.0."
HOMEPAGE = "https://launchpad.net/glmark2"
BUGTRACKER = "https://bugs.launchpad.net/glmark2"

LICENSE = "GPLv3+ & SGIv1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.SGI;beginline=5;md5=269cdab4af6748677acce51d9aa13552"

DEPENDS = "libpng12 jpeg"

SRC_URI = "https://launchpad.net/${BPN}/trunk/${PV}/+download/${BP}.tar.gz"

SRC_URI[md5sum] = "739859cf57d4c8a23452c43e84f66e56"
SRC_URI[sha256sum] = "bded41aaf918ce062d9b81e42cc5be943e6a80bc4ff9d046983b96102c3df6b5"

inherit waf pkgconfig

PACKAGECONFIG ?= "gl gles2"

PACKAGECONFIG[gl] = ",,virtual/libgl"
PACKAGECONFIG[gles2] = ",,virtual/libgles2"

EXTRA_OECONF += " --with-flavors=wayland-glesv2,wayland-gl"

