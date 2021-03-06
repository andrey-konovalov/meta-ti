python __anonymous() {
    features = bb.data.getVar("MACHINE_FEATURES", d, 1)
    if not features:
        return
    if "mmip" not in features:
        raise bb.parse.SkipPackage('ipumm-fw does not apply to systems without the "mmip" flag in MACHINE_FEATURES')
}

DESCRIPTION = "Firmware for IPU for supporting Accelerated MM decode and encode"
LICENSE = "TI-TSPA"

LIC_FILES_CHKSUM = "file://Texas_Instruments_ipumm_Manifest.pdf;md5=036f6300761559fbc5ce7d06e1ccea5a"

COMPATIBLE_MACHINE = "dra7xx"

RDEPENDS_${PN} = " libdce"

SRC_URI = "git://git.ti.com/ivimm/ipumm.git;protocol=git"

SRCREV = "486aa14f700783dbee63a0d81fb2e57d27a286e6"

S = "${WORKDIR}/git"

PV = "3.00.09.01"

require recipes-ti/includes/ti-paths.inc
require recipes-ti/includes/ti-staging.inc

DEPENDS = "ti-xdctools ti-sysbios ti-codec-engine ti-framework-components ti-xdais ti-ccsv6-native ti-ipc-rtos"

export HWVERSION="ES10"
export BIOSTOOLSROOT="${STAGING_DIR_TARGET}/usr/share/ti"

export XDCVERSION="ti-xdctools-tree"
export BIOSVERSION="ti-sysbios-tree"
export IPCVERSION="ti-ipc-tree"
export CEVERSION="ti-codec-engine-tree"
export FCVERSION="ti-framework-components-tree"
export XDAISVERSION="ti-xdais-tree"

export TMS470CGTOOLPATH="${M4_TOOLCHAIN_INSTALL_DIR}"
export IPCSRC="${STAGING_DIR_TARGET}/usr/share/ti/ti-ipc-tree"

do_configure() {
    oe_runmake unconfig
    oe_runmake vayu_smp_config
}

do_compile() {
    oe_runmake
}

TARGET = "dra7-ipu2-fw.xem4"

do_install() {
        mkdir -p ${D}${base_libdir}/firmware
        cp ${S}/${TARGET} ${D}${base_libdir}/firmware/${TARGET}
}

FILES_${PN} += "${base_libdir}/firmware/${TARGET}"

PR = "r2"
