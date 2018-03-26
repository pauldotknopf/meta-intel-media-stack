PACKAGECONFIG += "\
	${@bb.utils.contains('DISTRO_FEATURES', 'mfx', 'mfx', '', d)}\
	"
PACKAGECONFIG[mfx] = "--enable-libmfx,--disable-libmfx,msdk"
# Append 'mfx' to DISTRO_FEATURES to compile ffmpeg with this feature
# EXTRA_OECONF += "--enable-libmfx" 
# DEPENDS += "msdk" 
