SUMMARY = "Intel Media SDK gstreamer plugin"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://github.com/ishmael1985/gstreamer-media-SDK.git;protocol=https;branch=master"

SRCREV = "2b1a65d3287481bede34e85dbfd805e6fac039fa"
PV = "git${SRCPV}"

#SRC_URI += "file://0001-fix-lib-paths-for-mfx.patch"

inherit meson

S = "${WORKDIR}/git"

DEPENDS += "wayland virtual/mesa libxrandr libxkbcommon msdk gstreamer1.0 gstreamer1.0-plugins-base libva libdrm gstreamer1.0-plugins-bad"

#export MFX_HOME = "${STAGING_DIR_HOST}/usr"

EXTRA_OEMESON += "-Denable-builder=false"
#EXTRA_OECMAKE = "-DCMAKE_INSTALL_PREFIX=${libdir}/gstreamer-1.0 -DWITH_WAYLAND=OFF -DWITH_X11=OFF"

FILES_${PN} += "${libdir}/gstreamer-1.0"
FILES_${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"