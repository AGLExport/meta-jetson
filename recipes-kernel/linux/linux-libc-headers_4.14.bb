require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KV = "4.14.53"
S = "${WORKDIR}/linux-${KV}"

PR = "r53"
SRC_URI = "https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${KV}.tar.xz \
"

SRC_URI[md5sum] = "bfbc0300cfac4b00fd7cee1d95082d92"
SRC_URI[sha256sum] = "a85f2572f97dc551f4a159d0c0858e6f40b925afd2d14a0aa25ee9238da80bbf"
