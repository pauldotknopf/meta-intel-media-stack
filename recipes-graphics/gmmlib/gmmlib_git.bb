SUMMARY = "Intel(R) Graphics Memory Management Library"

HOMEPAGE = "https://github.com/intel/gmmlib"
BUGTRACKER = "https://github.com/intel/gmmlib/issues"

SECTION = "x11"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=d9a6e772cd4c362ee4c8ef87c5aad843"

SRC_URI = "git://github.com/intel/gmmlib.git"
SRCREV = "5b61c8a6b1882e4f83d339be6944dc1d434f3fa9"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE += " \
      -DRUN_TEST_SUITE=OFF \
    "

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"