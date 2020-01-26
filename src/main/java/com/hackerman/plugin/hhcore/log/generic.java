package com.hackerman.plugin.hhcore.log;

import static com.hackerman.plugin.main.pluginPrefix;

public class generic {
    public static void logMessage(String message) {
        System.out.println("[~] [" + pluginPrefix + "] " + message);
    }
    public static void logInstall(String message) {
        System.out.println("[~] [" + pluginPrefix + "] [INSTALL] " + message);
    }
}
