FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://pkgconfig-fix.patch"

EXTRA_OECONF += "--enable-libmfx"
DEPENDS += "msdk"