SUMMARY = "Intel Media Driver for VAAPI"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

SECTION = "x11"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0"

SRC_URI = "git://github.com/intel/media-driver.git"
SRCREV = "6819999ba209fab2e02c3d66a3d443e0164a7f66"
PV = "git${SRCPV}"

SRC_URI += "file://vaapi-env.conf"

S = "${WORKDIR}/git"

DEPENDS += "libva libpciaccess gmmlib"

inherit cmake pkgconfig

EXTRA_OECMAKE += " \
      -DMEDIA_RUN_TEST_SUITE=OFF \
    "

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/system.conf.d/
    install -m 0444 ${WORKDIR}/vaapi-env.conf ${D}${sysconfdir}/systemd/system.conf.d/
}

FILES_${PN} += "${libdir} ${libdir}/dri"

INSANE_SKIP_${PN} = "ldflags package_qa_hash_style already-stripped"
