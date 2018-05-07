SUMMARY = "Intel Media Driver for VAAPI"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

SECTION = "x11"
LICENSE = "Intel"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0"

SRC_URI = "git://github.com/intel/media-driver.git"
SRCREV = "ab264dd51f20ea83d6c40a886fb685ce372c47ba"

SRCREV_mediadriver ?= "ab264dd51f20ea83d6c40a886fb685ce372c47ba"
SRCREV_gmmlib ?= "b8d74ac3fcea0dffa901ce62399d43a937212074"
SRCREV_FORMAT = "mediadriver"
PV = "git${SRCPV}"

SRC_URI = "git://github.com/intel/media-driver.git;name=mediadriver;destsuffix=media-driver \
           git://github.com/intel/gmmlib.git;name=gmmlib;destsuffix=gmmlib"

SRC_URI += "file://0001-media-driver-disable-tests.patch"
SRC_URI += "file://0002-gmmlib-disable-tests.patch"
SRC_URI += "file://vaapi-env.conf"

S = "${WORKDIR}/media-driver"

DEPENDS += "libva libpciaccess"

inherit cmake pkgconfig

EXTRA_OECMAKE += " \
	  -DMEDIA_VERSION=2.0.0 \
      -DBUILD_ALONG_WITH_CMRTLIB=1 \
      -DBS_DIR_GMMLIB=${WORKDIR}/gmmlib/Source/GmmLib/ \
      -DBS_DIR_COMMON=${WORKDIR}/gmmlib/Source/Common/ \
      -DBS_DIR_INC=${WORKDIR}/gmmlib/Source/inc/ \
      -DBS_DIR_MEDIA=${WORKDIR}/media-driver \
        "

do_patch() {
    cd media-driver 
    patch -p1 < ${WORKDIR}/0001-media-driver-disable-tests.patch 
    cd ../gmmlib 
    patch -p1 < ${WORKDIR}/0002-gmmlib-disable-tests.patch 
}

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/system.conf.d/
    install -m 0444 ${WORKDIR}/vaapi-env.conf ${D}${sysconfdir}/systemd/system.conf.d/
}

FILES_${PN} += "${libdir} ${libdir}/dri"

INSANE_SKIP_${PN} = "ldflags package_qa_hash_style already-stripped"
