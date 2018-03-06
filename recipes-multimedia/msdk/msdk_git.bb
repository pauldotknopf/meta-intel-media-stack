SUMMARY = "Intel Media SDK samples and binaries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3cb331af679cd8f968bf799a9c55b46e"

SRC_URI = "git://github.com/Intel-Media-SDK/MediaSDK.git"
SRCREV = "e54022c1dbdcbd93b502f0da37352c9e9d103180"

SRC_URI += "file://0001-dont-override-cross-compile-flags.patch"
SRC_URI += "file://0002-dont-build-samples.patch"
SRC_URI += "file://0003-dont-override-cross-compile-flags.patch"
SRC_URI += "file://0004-add-libs-to-pkgconfig.patch"

DEPENDS += "libva media-driver gcc-runtime"

inherit cmake pkgconfig perlnative

S = "${WORKDIR}/git"
B = "${WORKDIR}/git/__cmake/intel64.make.release"


OECMAKE_EXTRA_ROOT_PATH += "${WORKDIR}/git"
CFLAGS += "-fPIC"
CXXFLAGS += "-fPIC"

do_configure() {
    cd ${WORKDIR}/git
    perl ${WORKDIR}/git/tools/builder/build_mfx.pl --no-warn-as-error --cross=${WORKDIR}/toolchain.cmake --cmake=intel64.make.release --prefix /usr
}

do_install_append() {
    rm ${D}/usr/samples/libvpp_plugin.a
    cp ${D}${libdir}/pkgconfig/mfx.pc ${D}${libdir}/pkgconfig/libmfx.pc
    install -d ${D}${includedir}/mfx
    file ${D}${includedir}
    ls ${D}${includedir}/*
    cp ${D}${includedir}/*.h ${D}${includedir}/mfx
}

PACKAGES =+ "${PN}-samples"

FILES_${PN} += "/usr/lib/*.so /usr/plugins/*"
#FILES_${PN}-staticdev += "/opt/intel/mediasdk/lib/*.a"
FILES_${PN}-samples += "/usr/samples/*"
FILES_${PN}-dev = "${includedir}/* ${libdir}/pkgconfig/*"
#FILES_${PN}-dev += "/opt/intel/mediasdk/include /opt/intel/mediasdk/lib/pkgconfig/*"

SYSROOT_PREPROCESS_FUNCS += "msdk_populate_sysroot"
msdk_populate_sysroot() {
        sysroot_stage_dir ${D}/usr ${SYSROOT_DESTDIR}/usr
}