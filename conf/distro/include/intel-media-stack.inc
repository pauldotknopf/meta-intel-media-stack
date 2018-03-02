PREFERRED_PROVIDER_virtual/kernel = "linux-intel-media-stack"
MACHINE_HWCODECS_remove += "va-intel gstreamer1.0-vaapi"
MACHINE_HWCODECS_append += "msdk-media gstreamer-msdk"

PNBLACKLIST[gstreamer1.0-vaapi] = "use gstreamer-msdk instead"
PNBLACKLIST[intel-gpu-tools] = "doesn't work on kernel used for msdk"