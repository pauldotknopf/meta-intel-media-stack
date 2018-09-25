
SUMMARY = "Video Acceleration (VA) API tests for Linux"

SECTION = "x11"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://Makefile.am;md5=87d7f9d5da59c9b214c6a47b728725e4;beginline=1;endline=21"

SRC_URI = "git://github.com/intel/libva-utils.git;branch=master"
SRCREV = "a8a9d73dad32a9a595080f5bf568127d4408d260"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS += "libdrm libva"

inherit autotools pkgconfig distro_features_check

REQUIRED_DISTRO_FEATURES = "opengl"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'wayland x11', d)}"
PACKAGECONFIG[x11] = "--enable-x11,--disable-x11,virtual/libx11 libxext libxfixes"
PACKAGECONFIG[wayland] = "--enable-wayland,--disable-wayland,wayland-native wayland"