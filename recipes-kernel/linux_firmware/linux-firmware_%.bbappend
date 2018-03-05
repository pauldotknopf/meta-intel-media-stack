SRCREV_gittip = "7344ec9e1df9e27d105ed48d2db99e22370236de"

SRC_URI += "git://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git;name=gittip;destsuffix=gittip"

PV = "0.0+git${SRCREV}"

# SRCREV = "bf04291309d3169c0ad3b8db52564235bbd08e30"
# PE = "1"
# PV = "0.0+git${SRCPV}"

# SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git"

do_install_append() {
    rm "${D}/lib/firmware/i915/skl_dmc_ver1_23.bin"
    rm "${D}/lib/firmware/i915/skl_dmc_ver1_26.bin"
    rm "${D}/lib/firmware/i915/skl_dmc_ver1.bin"
    cp "${WORKDIR}/gittip/i915/skl_dmc_ver1_27.bin" "${D}/lib/firmware/i915/skl_dmc_ver1_27.bin"
    ln -s "skl_dmc_ver1_27.bin" "${D}/lib/firmware/i915/skl_dmc_ver1.bin"
}