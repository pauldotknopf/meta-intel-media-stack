SUMMARY = "Intel Media Driver for Media SDK"
DESCRIPTION = "Intel iHD Media Driver for Media SDK"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

RPM_PATH="${THISDIR}/files"

SRC_URI = "${LINUX_MEDIA_SERVER_STUDIO_LOCATION}"
SRC_URI[md5sum] = "53c61007222244ebf1add8e86168c94b"
SRC_URI = "\
        file://vaapi-env.sh \
        file://vaapi-env.conf \
        "

DEPENDS = "libdrm virtual/mesa virtual/libgles1 virtual/libgles2 virtual/egl libva"
RDEPENDS_${PN} += " libdrm"

INSANE_SKIP_${PN} += " already-stripped dev-so useless-rpaths"

do_install() {
        mkdir -p ${D}${libdir}
        for f in ${WORKDIR}/opt/intel/mediasdk/lib64/*; do
                install $f ${D}${libdir};
        done
        for f in ${WORKDIR}/opt/intel/common/mdf/lib64/*; do
                install $f ${D}${libdir};
        done
        install -d ${D}${sysconfdir}/profile.d
	install -m 0755 ${WORKDIR}/vaapi-env.sh ${D}${sysconfdir}/profile.d/
        install -d ${D}${sysconfdir}/systemd/system.conf.d/
        install -m 0755 ${WORKDIR}/vaapi-env.conf ${D}${sysconfdir}/systemd/system.conf.d/
}

# Disable some QA checks that file with the precompiled binaries.
#INHIBIT_PACKAGE_DEBUG_SPLIT="1"
INSANE_SKIP_${PN} = "ldflags package_qa_hash_style already-stripped"

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dev = ""