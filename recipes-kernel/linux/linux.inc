DESCRIPTION = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo

# Enable OABI compat for people stuck with obsolete userspace
ARM_KEEP_OABI ?= "1"

# Quirk for udev greater or equal 141
UDEV_GE_141 ?= "1"

# Enable cgroups support for systemd
KERNEL_ENABLE_CGROUPS ?= "1"

# combine oe-core way with angstrom DISTRO_TYPE
DISTRO_TYPE ?= "${@bb.utils.contains("IMAGE_FEATURES", "debug-tweaks", "debug", "release",d)}"

# Set the verbosity of kernel messages during runtime
# You can define CMDLINE_DEBUG in your local.conf or distro.conf to override this behaviour
CMDLINE_DEBUG ?= '${@base_conditional("DISTRO_TYPE", "release", "quiet", "debug", d)}'
CMDLINE_append = " ${CMDLINE_DEBUG}"

# Set a variable in .configure
# $1 - Configure variable to be set
# $2 - value [n/y/value]
kernel_configure_variable() {
	# Remove the config
	CONF_SED_SCRIPT="$CONF_SED_SCRIPT /CONFIG_$1[ =]/d;"
	if test "$2" = "n"
	then
		echo "# CONFIG_$1 is not set" >> ${B}/.config
	else
		echo "CONFIG_$1=$2" >> ${B}/.config
	fi
}

# returns all the elements from the src uri that are .cfg files
def find_cfgs(d):
    sources=src_patches(d, True)
    sources_list=[]
    for s in sources:
        if s.endswith('.cfg'):
            sources_list.append(s)

    return sources_list

do_configure_prepend() {
	# Clean .config
	echo "" > ${B}/.config
	CONF_SED_SCRIPT=""

	# oabi / eabi support
	if [ "${TARGET_OS}" = "linux-gnueabi" -o  "${TARGET_OS}" = "linux-uclibceabi" ]; then
		kernel_configure_variable AEABI y
		if [ "${ARM_KEEP_OABI}" = "1" ] ; then
			kernel_configure_variable OABI_COMPAT y
		else
			kernel_configure_variable OABI_COMPAT n
		fi
	else
		kernel_configure_variable AEABI n
		kernel_configure_variable OABI_COMPAT n
	fi

	# Set cmdline
	kernel_configure_variable CMDLINE "\"${CMDLINE}\""

	# Localversion
	kernel_configure_variable LOCALVERSION "\"\""
	kernel_configure_variable LOCALVERSION_AUTO n

	# Distribution-dependent kernel features
	case "${DISTRO_FEATURES}" in
		*bluetooth*) do_configure_bluetooth
	esac

	# Udev quirks
	# Newer versions of udev mandate that sysfs doesn't have deprecated entries
	if [ "${UDEV_GE_141}" = "1" ] ; then
		kernel_configure_variable SYSFS_DEPRECATED n
		kernel_configure_variable SYSFS_DEPRECATED_V2 n
		kernel_configure_variable HOTPLUG y
		kernel_configure_variable UEVENT_HELPER_PATH "\"\""
		kernel_configure_variable UNIX y
		kernel_configure_variable SYSFS y
		kernel_configure_variable PROC_FS y
		kernel_configure_variable TMPFS y
		kernel_configure_variable INOTIFY_USER y
		kernel_configure_variable SIGNALFD y
		kernel_configure_variable TMPFS_POSIX_ACL y
		kernel_configure_variable BLK_DEV_BSG y
		kernel_configure_variable DEVTMPFS y
		kernel_configure_variable DEVTMPFS_MOUNT y
	fi

	# Newer inits like systemd need cgroup support
	if [ "${KERNEL_ENABLE_CGROUPS}" = "1" ] ; then
		kernel_configure_variable CGROUP_SCHED y
		kernel_configure_variable CGROUPS y
		kernel_configure_variable CGROUP_NS y
		kernel_configure_variable CGROUP_FREEZER y
		kernel_configure_variable CGROUP_DEVICE y
		kernel_configure_variable CPUSETS y
		kernel_configure_variable PROC_PID_CPUSET y
		kernel_configure_variable CGROUP_CPUACCT y
		kernel_configure_variable RESOURCE_COUNTERS y
	fi

	# root-over-nfs-over-usb-eth support. Limited, but should cover some cases
	# Enable this by setting a proper CMDLINE_NFSROOT_USB.
	if [ ! -z "${CMDLINE_NFSROOT_USB}" ]; then
		oenote "Configuring the kernel for root-over-nfs-over-usb-eth with CMDLINE ${CMDLINE_NFSROOT_USB}"
		kernel_configure_variable INET y
		kernel_configure_variable IP_PNP y
		kernel_configure_variable USB_GADGET y
		kernel_configure_variable USB_GADGET_SELECTED y
		kernel_configure_variable USB_ETH y
		kernel_configure_variable NFS_FS y
		kernel_configure_variable ROOT_NFS y
		kernel_configure_variable ROOT_NFS y
		kernel_configure_variable CMDLINE "\"${CMDLINE_NFSROOT_USB}\""
	fi

	# Activate CONFIG_LEGACY_PTYS
	kernel_configure_variable LEGACY_PTYS y
	kernel_configure_variable LEGACY_PTY_COUNT 8

        # needed for ttySC6 login on koelsch with systemd
        kernel_configure_variable FHANDLE y

        # needed for USB touchscreens
        kernel_configure_variable HID_MULTITOUCH y

        # additional configs needed for Genivi compliance
        kernel_configure_variable EXPERIMENTAL y
        kernel_configure_variable ECRYPT_FS y
        kernel_configure_variable QUOTA y
        kernel_configure_variable BT y

        # add uinput for automated testing
        kernel_configure_variable INPUT_MISC y
        kernel_configure_variable INPUT_UINPUT y

	# psplash only
	kernel_configure_variable FRAMEBUFFER_CONSOLE n
	kernel_configure_variable LOGO n

	# systemd requires following
	kernel_configure_variable AUTOFS4_FS y
	kernel_configure_variable IPV6 y
	kernel_configure_variable SCHEDSTATS y

	# AGL spec. mentioned following
	kernel_configure_variable EXT4_FS y
	kernel_configure_variable FANOTIFY y
	kernel_configure_variable POSIX_MQUEUE y
	kernel_configure_variable TUN y

	# For containers support (AGL spec v1.0, section 7.3.1)
	kernel_configure_variable NAMESPACES y
	kernel_configure_variable UTS_NS n
	kernel_configure_variable IPC_NS y
	kernel_configure_variable USER_NS y
	kernel_configure_variable PID_NS y
	kernel_configure_variable NET_NS y

	# nbdroot support
	kernel_configure_variable BLK_DEV_LOOP y
	kernel_configure_variable BLK_DEV_NBD y
	kernel_configure_variable BLK_DEV_INITRD y
	kernel_configure_variable RD_GZIP y
	kernel_configure_variable RD_BZIP2 y
	kernel_configure_variable RD_XZ y
	kernel_configure_variable EXT4_FS y
	kernel_configure_variable EXT4_USE_FOR_EXT23 y
	kernel_configure_variable BLK_DEV_RAM y
	kernel_configure_variable BLK_DEV_RAM_COUNT 2
	kernel_configure_variable BLK_DEV_RAM_SIZE 16384

	# only when distro is built for debug
	if [ "${DISTRO_TYPE}" = "debug" ]; then
		kernel_configure_variable BLK_DEV_LOOP y
	fi

	if [ "x1" = "x${@bb.utils.contains("DISTRO_FEATURES", "usbgadget", "1", "",d)}" ]; then
		# activate USB-OTG & Gadget
		kernel_configure_variable USB_OTG y
		kernel_configure_variable USB_RENESAS_USBHS_UDC y
		kernel_configure_variable CONFIGFS_FS y
		kernel_configure_variable USB_GADGET y
		kernel_configure_variable USB_LIBCOMPOSITE y
		kernel_configure_variable USB_F_ACM y
		kernel_configure_variable USB_U_SERIAL y
		kernel_configure_variable USB_ETH m
		kernel_configure_variable USB_G_MULTI m
		kernel_configure_variable USB_G_MULTI_RNDIS n
		kernel_configure_variable USB_G_MULTI_CDC y

		# ethernet over usb tethering
		kernel_configure_variable NETFILTER y
		kernel_configure_variable NF_CONNTRACK_IPV4 y
		kernel_configure_variable NF_NAT_IPV4 y
		kernel_configure_variable BRIDGE y
		kernel_configure_variable IP_NF_TARGET_MASQUERADE y
	fi

	# Keep this the last line
	# Remove all modified configs and add the rest to .config
	sed -e "${CONF_SED_SCRIPT}" < '${WORKDIR}/defconfig' >> '${B}/.config'

	# add cfgs from SRC_URI.
	cfgs="${@" ".join(find_cfgs(d))}"
	cat ${cfgs} >> '${B}/.config'

	yes '' | oe_runmake -C ${S} O=${B} oldconfig
}

do_configure_bluetooth() {
    # Needed for Bluetooth hardware support
    kernel_configure_variable BT_HIDP y
    kernel_configure_variable BT_HCIBTUSB m
    kernel_configure_variable BT_BNEP m
    kernel_configure_variable BT_BNEP_MC_FILTER y
    kernel_configure_variable BT_BNEP_PROTO_FILTER y
    kernel_configure_variable BT_RFCOMM m
    kernel_configure_variable BT_RFCOMM_TTY y
    kernel_configure_variable RFKILL m
}

# Automatically depend on lzop-native if CONFIG_KERNEL_LZO is enabled
python () {
    try:
        defconfig = bb.fetch2.localpath('file://defconfig', d)
    except bb.fetch2.FetchError:
        return

    try:
        configfile = open(defconfig)
    except IOError:
        return

    if 'CONFIG_KERNEL_LZO=y\n' in configfile.readlines():
        depends = d.getVar('DEPENDS', False)
        d.setVar('DEPENDS', depends + ' lzop-native')
}
