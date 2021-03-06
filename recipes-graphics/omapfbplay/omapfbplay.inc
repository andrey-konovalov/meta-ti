DESCRIPTION = "Simple libav-based player that uses the omapfb overlays"
DEPENDS = "bzip2 lame libav virtual/kernel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://omapfbplay.c;beginline=2;endline=22;md5=05d3789ab362e6f4a9682e98100ea0fa"

PV = "0.0+${PR}+gitr${SRCREV}"

SRCREV = "34293052c5a2ae328eac6903512e6b4ce19b5639"
SRC_URI = "git://git.mansr.com/omapfbplay;protocol=git \
           file://omapfbplay-errorhandling.patch \
           file://0001-omapfbplay-switch-CODEC_TYPE_VIDEO-to-AVMEDIA_TYPE_V.patch \
          "

S = "${WORKDIR}/git"

# We want a kernel header for armv7a, but we don't want to make mplayer machine specific for that
STAGING_KERNEL_DIR = "${STAGING_DIR}/${MACHINE_ARCH}${TARGET_VENDOR}-${TARGET_OS}/kernel"
CFLAGS += " -I. -I${STAGING_KERNEL_DIR}/include "

OMAPFBPLAYVARIANT ?= "${PN}"
OMAPFBPLAYOPTS ?= "V4L2=y NETSYNC=y"

do_compile() {
	cp ${STAGING_KERNEL_DIR}/arch/arm/plat-omap/include/mach/omapfb.h ${S} || true
	cp ${STAGING_KERNEL_DIR}/include/asm-arm/arch-omap/omapfb.h ${S} || true
	cp ${STAGING_KERNEL_DIR}/include/linux/omapfb.h ${S} || true
	oe_runmake ${TARGET_ARCH}=y ${OMAPFBPLAYOPTS} -e
}

do_install() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/omapfbplay ${D}/${bindir}/${OMAPFBPLAYVARIANT}
}
