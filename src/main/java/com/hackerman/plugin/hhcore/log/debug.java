package com.hackerman.plugin.hhcore.log;

import com.eu.habbo.Emulator;

import static com.hackerman.plugin.main.pluginName;

public class debug {
    public static void logDebug(String module, String message) {
        if (Emulator.getConfig().getValue("hh.debug").equalsIgnoreCase("true")) {
            System.out.println("[@] " + pluginName + " [" + module + "] " + message);
        }
    }
}
