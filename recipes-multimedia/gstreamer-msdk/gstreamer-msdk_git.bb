SUMMARY = "Intel Media SDK gstreamer plugin"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://github.com/intel/gstreamer-media-SDK.git;protocol=https;branch=master"

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"

inherit cmake

S = "${WORKDIR}/git"

DEPENDS += "wayland virtual/mesa libxrandr libxkbcommon msdk gstreamer1.0 gstreamer1.0-plugins-base libva libdrm gstreamer1.0-plugins-bad"

export MFX_HOME = "${STAGING_DIR_HOST}/opt/intel/mediasdk"

CFLAGS += "-I${STAGING_DIR_HOST}/opt/intel/mediasdk/include"
EXTRA_OECMAKE = "-DCMAKE_INSTALL_PREFIX=${libdir}/gstreamer-1.0 -DWITH_WAYLAND=OFF -DWITH_X11=OFF"

FILES_${PN} += "${libdir}/gstreamer-1.0"
FILES_${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"