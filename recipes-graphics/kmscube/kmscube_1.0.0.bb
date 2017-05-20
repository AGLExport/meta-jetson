SUMMARY = "Demo application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://kmscube.c;beginline=1;endline=23;md5=8b309d4ee67b7315ff7381270dd631fb"

DEPENDS = "libdrm virtual/egl"

inherit autotools pkgconfig


SRC_URI = "git://github.com/Gnurou/kmscube.git;protocol=git;branch=master \
           "

S = "${WORKDIR}/git"

SRCREV = "cf1b4e2ba941cad2ebd740a111fd61a9c083198c"

INSANE_SKIP_kmscube += "dev-deps"

FILESEXTRAPATHS_prepend := "${THISDIR}:"
