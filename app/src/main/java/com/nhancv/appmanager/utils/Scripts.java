/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.utils;

import at.amartinz.execution.BusyBox;

/**
 * Defines and runs Scripts.
 */
public class Scripts {

    public static final String BUILD_PROP = "/system/build.prop";
    public static final String SYSCTL = "/system/etc/sysctl.conf";

    private static final String APPEND_CMD = "echo \"%s=%s\" >> %s;";
    private static final String COPY_CMD = "cp %s %s;";
    private static String KILL_PROP_CMD;
    private static String REPLACE_CMD;

    public static String copyFile(final String source, final String destination) {
        return String.format(COPY_CMD, source, destination);
    }

    public static String addOrUpdate(final String property, final String value) {
        return addOrUpdate(property, value, BUILD_PROP);
    }

    public static String addOrUpdate(final String property, final String value, final String file) {
        if (Utils.existsInFile(file, property)) {
            if (REPLACE_CMD == null) {
                REPLACE_CMD = BusyBox.callBusyBoxApplet("sed", "-i \"/%s/ c %<s=%s\" %s;");
            }
            if (REPLACE_CMD == null) {
                return "";
            }
            return String.format(REPLACE_CMD, property, value, file);
        } else {
            return String.format(APPEND_CMD, property, value, file);
        }
    }

    public static String removeProperty(final String property) {
        return removeProperty(property, BUILD_PROP);
    }

    public static String removeProperty(final String property, final String file) {
        if (Utils.existsInFile(file, property)) {
            if (KILL_PROP_CMD == null) {
                KILL_PROP_CMD = BusyBox.callBusyBoxApplet("sed", "-i \"/%s/D\" %s;");
            }
            if (KILL_PROP_CMD == null) {
                return "";
            }
            return String.format(KILL_PROP_CMD, property, file);
        }
        return "";
    }

}
