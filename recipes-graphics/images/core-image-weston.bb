SUMMARY = "A very basic Wayland image with a terminal"

IMAGE_FEATURES += " package-management ssh-server-dropbear hwcodecs "

LICENSE = "MIT"

inherit core-image distro_features_check

REQUIRED_DISTRO_FEATURES = "wayland"

CORE_IMAGE_BASE_INSTALL += "weston weston-init weston-examples gtk+3-demo clutter-1.0-examples "

IMAGE_INSTALL += " kernel-modules udev-extraconf libdrm-tegra libdrm-kms mesa-megadriver kmscube "

IMAGE_INSTALL += " glmark2 alsa-utils"

#IMAGE_INSTALL += " glmark2 mpv jack2 jack2conf alsa-utils"

#IMAGE_INSTALL += " qtwayland qtbase-fonts qtbase-plugins cinematicexperience "

