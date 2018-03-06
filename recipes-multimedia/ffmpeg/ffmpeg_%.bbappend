FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://pkgconfig-fix.patch"

PACKAGECONFIG += "mfx"
PACKAGECONFIG[mfx] = "--enable-libmfx,--disable-libmfx,msdk"