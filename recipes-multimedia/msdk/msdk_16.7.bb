SUMMARY = "Intel Media SDK samples and binaries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://opt/intel/mediasdk/include/mfxcommon.h;beginline=3;endline=25;md5=a193f7859c930260e0ac464c81b56ffa"

#RPM_PATH="${THISDIR}/files"

#SRC_URI = "file://${RPM_PATH}/intel-linux-mediasdk-${PV}-*.rpm;subdir=${BP};name=msdk-rpm \
#           file://${RPM_PATH}/intel-linux-mediasdk-devel-${PV}-*.rpm;subdir=${BP};name=msdk-rpm"

SRC_URI = "${LINUX_MEDIA_SERVER_STUDIO_LOCATION}"
SRC_URI[md5sum] = "53c61007222244ebf1add8e86168c94b"

DEPENDS += "libva"
RDEPENDS_${PN} += " wayland perl"
RDEPENDS_${PN}-samples += " perl"

INSANE_SKIP_${PN} = " already-stripped dev-so staticdev"
INSANE_SKIP_${PN}-plugins = " ldflags"

inherit bin_package

PACKAGE_BEFORE_PN = "${PN}-samples ${PN}-plugins"

SYSROOT_PREPROCESS_FUNCS += "msdk_populate_sysroot"
msdk_populate_sysroot() {
        sysroot_stage_dir ${D}/opt ${SYSROOT_DESTDIR}/opt
}

FILES_${PN}-doc = "/opt/intel/mediasdk/doc"

FILES_${PN}-samples = "/opt/intel/mediasdk/samples/"

FILES_${PN}-dev = "/opt/intel/mediasdk/include \
                   /opt/intel/mediasdk/opensource \
                   /opt/intel/mediasdk/builder \
                   "

FILES_${PN}-plugins = "/opt/intel/mediasdk/plugins"

do_unpack_msdk() {
    mv ${WORKDIR}/opt/ ${S}
    mv ${WORKDIR}/etc/ ${S}
    rm -r ${S}/opt/intel/common
    rm -r ${S}/opt/intel/mediasdk/lib64/iHD_drv_video.so
    rm -r ${S}/etc/ld.so.conf.d
    find ${S}/etc/profile.d ! -name 'intel-mediasdk-devel.sh' -type f -exec rm -f {} +
}

do_unpack[postfuncs] += "do_unpack_msdk"

#INSANE_SKIP_${PN} = "ldflags package_qa_hash_style already-stripped staticdev package_qa_check_dev"