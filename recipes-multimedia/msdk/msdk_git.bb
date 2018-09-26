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

do_install_append() {
  rm ${D}/usr/share/mfx/samples/libvpp_plugin.a
  # See here: https://github.com/Intel-Media-SDK/MediaSDK/issues/671
  ln -s libmfxhw64.so.1.28 ${D}/usr/lib/libmfxhw64-p.so.1.28
}

OECMAKE_EXTRA_ROOT_PATH = "${S}/api"

PACKAGES =+ "${PN}-samples"

FILES_${PN} += "/usr/lib/*.so /usr/lib/mfx/*.so /usr/lib/mfx/*.cfg"
FILES_${PN}-samples += "/usr/share/mfx/samples/*"