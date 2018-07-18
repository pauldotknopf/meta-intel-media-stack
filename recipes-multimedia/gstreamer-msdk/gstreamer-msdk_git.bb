SUMMARY = "Intel Media SDK gstreamer plugin"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://github.com/intel/gstreamer-media-SDK.git;protocol=https;branch=master"

SRCREV = "060718e15d4bd628944eabfd1b09ef13ef8934e8"
PV = "git${SRCPV}"

inherit meson

S = "${WORKDIR}/git"

DEPENDS += "virtual/mesa libxrandr libxkbcommon msdk gstreamer1.0 gstreamer1.0-plugins-base libva libdrm gstreamer1.0-plugins-bad"

EXTRA_OEMESON += "-DMFX_HOME=${STAGING_EXECPREFIXDIR}"
EXTRA_OEMESON += "-DWITH_MSS_2016=false"

do_install_append() {
    install -d ${D}${libdir}/gstreamer-1.0
    cp -L ${D}${libdir}/*.so ${D}${libdir}/gstreamer-1.0
    rm ${D}${libdir}/*.so*
    echo test
}

FILES_${PN} += "${libdir}/gstreamer-1.0"