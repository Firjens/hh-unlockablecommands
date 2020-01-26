package com.hackerman.plugin.commandmanager;

public class commandExist {
    public static boolean checkIfCommandExists(String command) {
        // Preventing caps for ID reasons
        if (command.equals("kiss") || command.equals("tea") || command.equals("poof") || command.equals("eggify")) {
            return true;
        }
        return false;
    }
}
