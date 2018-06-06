SUMMARY = "Intel Media SDK samples and binaries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3cb331af679cd8f968bf799a9c55b46e"

SRC_URI = "git://github.com/MedXChange/MediaSDK.git"
SRCREV = "1ced846999c001b90a3af477710460e5425e5be7"

DEPENDS += "libva media-driver gcc-runtime"

inherit cmake pkgconfig perlnative

S = "${WORKDIR}/git"

OECMAKE_EXTRA_ROOT_PATH += "${WORKDIR}/git"
CFLAGS += "-fPIC"
CXXFLAGS += "-fPIC"

do_install_append() {
    rm ${D}/usr/samples/libvpp_plugin.a
    install -d ${D}${includedir}/mfx
    cp ${D}${includedir}/*.h ${D}${includedir}/mfx
}

PACKAGES =+ "${PN}-samples"

FILES_${PN} += "/usr/lib/*.so /usr/plugins/*"
FILES_${PN}-samples += "/usr/samples/*"
FILES_${PN}-dev = "${includedir}/* ${libdir}/pkgconfig/*"

SYSROOT_PREPROCESS_FUNCS += "msdk_populate_sysroot"
msdk_populate_sysroot() {
        sysroot_stage_dir ${D}/usr ${SYSROOT_DESTDIR}/usr
}