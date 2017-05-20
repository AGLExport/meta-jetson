SUMMARY = "libdrm Tegra K1 append recipe"


EXTRA_OECONF += " --enable-tegra-experimental-api \
                "

PACKAGES =+ " ${PN}-tegra "

RRECOMMENDS_${PN}-drivers += " ${PN}-tegra "

FILES_${PN}-tegra = "${libdir}/libdrm_tegra.so.*"

