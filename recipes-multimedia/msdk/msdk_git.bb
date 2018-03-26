SUMMARY = "Intel Media SDK"
SECTION="x11"
DESCRIPTION = "\
Intel Media SDK provides an API \
to access hardware-accelerated video decode, encode \
and filtering on Intel® platforms with integrated graphics.\
"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "\
	git://github.com/Intel-Media-SDK/MediaSDK;protocol=https;branch=master;name=sdk \
	file://0001-keepup-cflags.patch \
	"
# revision number from the lastest download (2018-03-21)
# use ${AUTOREV} for downloading since Intel doesn't release anything
SRCREV_sdk= "${@base_conditional('BB_NO_NETWORK', '1', 'bc9ac30da8126cd1ad23b41bd97bbfc9ded0053e', '${AUTOREV}', d)}"

DEPENDS += "libva media-driver"
RDEPENDS_${PN} += " libva media-driver"
RDEPENDS_${PN}-dev += " ${PN}"

# use of a perl script to configure
inherit perlnative cmake

# pick up files for extra packages
# reorder package building
PACKAGE_BEFORE_PN = "${PN}-samples ${PN}-plugins"
PACKAGES = "${PN}-dev ${PACKAGE_BEFORE_PN} ${PN}"

# discard debug packaging
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

# msdk is usually installed into /opt
SYSROOT_PREPROCESS_FUNCS += "msdk_populate_sysroot"
msdk_populate_sysroot() {
		sysroot_stage_dir ${D}/opt ${SYSROOT_DESTDIR}/opt
}

S="${WORKDIR}/git"
# see github README
# enforce /opt prefix, to make sure

MSDK_PREFIX?="/opt/intel/media/sdk"
#MSDK_PREFIX?="/usr"

do_configure (){
	cd ${S}
	perl tools/builder/build_mfx.pl --no-warn-as-error --cross=${WORKDIR}/toolchain.cmake --cmake=intel64.make.release --prefix ${MSDK_PREFIX}
}

# update pkgconfig repo
do_install_append () {
	if [ ! -d ${D}${libdir}/pkgconfig ]; then 
	install -d ${D}${libdir}/pkgconfig/ 
	cp ${D}${MSDK_PREFIX}/lib/pkgconfig/*.pc ${D}${libdir}/pkgconfig/ 
	fi
	# ffmpeg try to find out mfx libs into {pkgconfig}/mfx
	# https://software.intel.com/en-us/articles/accessing-intel-media-server-studio-for-linux-codecs-with-ffmpeg
	install -d ${D}${MSDK_PREFIX}/include/mfx/ 
	cp ${D}${MSDK_PREFIX}/include/*.h ${D}${MSDK_PREFIX}/include/mfx/
}

# perl script created the following repo for building
B = "${S}/__cmake/intel64.make.release"

OECMAKE_EXTRA_ROOT_PATH += "${S}"

# options:
#EXTRA_OECMAKE
PACKAGECONFIG ?= " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
PACKAGECONFIG[x11]="-DENABLE_X11=ON,-DENABLE_X11=OFF,virtual/libx11"
PACKAGECONFIG[opencl]="-DENABLE_OPENCL=ON,-DENABLE_OPENCL=OFF"

# packages

FILES_${PN} += "\
		${MSDK_PREFIX}/lib \
		"

INSANE_SKIP_${PN}-dev +="staticdev"
FILES_${PN}-dev = "\
		${MSDK_PREFIX}/lib/*.a \
		${MSDK_PREFIX}/include \
		${MSDK_PREFIX}/lib/pkgconfig \
		${libdir}/pkgconfig \
		"
FILES_${PN}-samples = "${MSDK_PREFIX}/samples/"

# ".a" libraries may be shipped within samples files
INSANE_SKIP_${PN}-samples+="staticdev"

FILES_${PN}-plugins = "\
	${MSDK_PREFIX}/plugins \
	"
	
