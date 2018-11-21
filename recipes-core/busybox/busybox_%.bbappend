# systemd-journald handles logging, don't need redundant busybox daemons
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://no-syslogd.cfg"
SYSTEMD_PACKAGES_remove = "${PN}-syslog"
