SUMMARY = "Intel Media SDK samples and binaries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3cb331af679cd8f968bf799a9c55b46e"

SRC_URI = "git://github.com/Intel-Media-SDK/MediaSDK.git"
SRCREV = "e54022c1dbdcbd93b502f0da37352c9e9d103180"

SRC_URI += "file://0001-dont-override-cross-compile-flags.patch"
SRC_URI += "file://0002-dont-build-samples.patch"
SRC_URI += "file://0003-dont-override-cross-compile-flags.patch"

DEPENDS += "libva media-driver gcc-runtime"

inherit cmake pkgconfig perlnative

S = "${WORKDIR}/git"
B = "${WORKDIR}/git/__cmake/intel64.make.release"


OECMAKE_EXTRA_ROOT_PATH += "${WORKDIR}/git"
CFLAGS += "-fPIC"
CXXFLAGS += "-fPIC"

do_configure() {
    cd ${WORKDIR}/git
    perl ${WORKDIR}/git/tools/builder/build_mfx.pl --no-warn-as-error --cross=${WORKDIR}/toolchain.cmake --cmake=intel64.make.release
}

do_install_append() {
    # We don't want to shit this with samples. It isn't needed.
    rm "${D}/opt/intel/mediasdk/samples/libvpp_plugin.a"
}

PACKAGES =+ "${PN}-samples"

FILES_${PN} += "/opt/intel/mediasdk/lib/*.so /opt/intel/mediasdk/plugins/*"
FILES_${PN}-staticdev += "/opt/intel/mediasdk/lib/*.a"
FILES_${PN}-samples += "/opt/intel/mediasdk/samples/*"
FILES_${PN}-dev += "/opt/intel/mediasdk/include /opt/intel/mediasdk/lib/pkgconfig/*"

SYSROOT_PREPROCESS_FUNCS += "msdk_populate_sysroot"
msdk_populate_sysroot() {
        sysroot_stage_dir ${D}/opt ${SYSROOT_DESTDIR}/opt
}