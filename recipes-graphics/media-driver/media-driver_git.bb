SUMMARY = "Intel Media Driver for VAAPI"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

SECTION = "x11"
LICENSE = "Intel"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0"

SRC_URI = "git://github.com/intel/media-driver.git"
SRCREV = "2eab2e248c5787ceaebd76be791e60e28e56fbf3"

SRCREV_mediadriver ?= "2eab2e248c5787ceaebd76be791e60e28e56fbf3"
SRCREV_gmmlib ?= "b1451bbe4c506f17ddc819f12b4b448fc08698c5"

SRC_URI = "git://github.com/intel/media-driver.git;name=mediadriver;destsuffix=media-driver \
           git://github.com/intel/gmmlib.git;name=gmmlib;destsuffix=gmmlib"

S = "${WORKDIR}/media-driver"

DEPENDS += "libva libpciaccess"

EXTRA_OECMAKE = " \
	  -DMEDIA_VERSION=2.0.0 \
      -DBUILD_ALONG_WITH_CMRTLIB=1 \
      -DBS_DIR_GMMLIB=`pwd`/../gmmlib/Source/GmmLib/ \
      -DBS_DIR_COMMON=`pwd`/../gmmlib/Source/Common/ \
      -DBS_DIR_INC=`pwd`/../gmmlib/Source/inc/ \
      -DBS_DIR_MEDIA=`pwd`/../media-driver \
        "

# -DMEDIA_VERSION="2.0.0" \
# -DBUILD_ALONG_WITH_CMRTLIB=1 \
# -DBS_DIR_GMMLIB=`pwd`/../gmmlib/Source/GmmLib/ \
# -DBS_DIR_COMMON=`pwd`/../gmmlib/Source/Common/ \
# -DBS_DIR_INC=`pwd`/../gmmlib/Source/inc/ \
# -DBS_DIR_MEDIA=`pwd`/../media-driver

inherit cmake pkgconfig