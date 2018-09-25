SUMMARY = "Intel Media SDK samples and binaries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3cb331af679cd8f968bf799a9c55b46e"

SRC_URI = "git://github.com/Intel-Media-SDK/MediaSDK.git"
SRCREV = "3e16d8da3d3249909b473b1dfedfb8d9960f09d7"

S = "${WORKDIR}/git"

DEPENDS += "libva media-driver gcc-runtime"

inherit cmake pkgconfig

do_configure_prepend() {
  export MFX_HOME=${S}/api/include
}

EXTRA_OECMAKE += "-DBUILD_SAMPLES=OFF"
OECMAKE_EXTRA_ROOT_PATH = "${S}/api"

FILES_${PN} += "/usr/lib/*.so /usr/lib/mfx/*.so /usr/lib/mfx/*.cfg"
