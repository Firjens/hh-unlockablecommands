package com.hackerman.plugin.hhcore.log;

import com.hackerman.plugin.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class error {
    public static void saveError(String module, String location, String error, Exception err) throws IOException {
        String current = new File(".").getCanonicalPath();
        Path path = Paths.get(current + "/hh-errors");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("[!] ERROR: Failed to create an directory (1)");
            }
        }
        // Date is included in it's name
        DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_HH_mm_ss_sss");
        DateFormat dateFormatVisible = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:ssss");
        Date date = new Date();
        // Path is hh-errors
        Path file = Paths.get(current + "/hh-errors/" + main.pluginShort + "_" + dateFormat.format(date) + ".txt");
        // Creating the .txt file
        Files.createFile(file);
        // Populatig the .txt file
        List<String> lines = Arrays.asList("plugin=" + main.pluginName, "pluginAuthor=" + main.pluginAuthor, "pluginVersion=" + main.version, "date=" + dateFormatVisible.format((date)), "module=" + module ,"stacktrace=" + err.getMessage());
        Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        System.out.println("[!] This error is now saved and can be found at: " + "hh-errors/" + main.pluginShort + "_" + dateFormat.format(date) + ".txt");
    }
    public static void logError(String module, String location, String error, int strength, boolean userisnoob, Exception err) {

        // Check if the error is user generated and display a different message
        if (userisnoob) {
            System.out.println("[!] ERROR: An unknown error has occured in " + module);
            System.out.println("[!] Plugin: " + main.pluginName + " version " + main.version + " author " + main.pluginAuthor );
            System.out.println("[!] Location: " + location);
            System.out.println("[!] STACKTRACE: Read this and interpret it! Do some debugging first before contacting us!");
            System.out.println("[!] " + err.getMessage() );
            System.out.println("[!] This is most likely not a bug in the plugin! Interpret the error and see why it's doing it before asking support!!");
        } else {
            System.out.println("[!] ERROR: An unknown error has occured in " + module);
            System.out.println("[!] Plugin: " + main.pluginName + " version " + main.version + " author " + main.pluginAuthor );
            System.out.println("[!] Location: " + location);
            System.out.println("[!] STACKTRACE:");
            System.out.println("[!] " + err.getMessage() );
            System.out.println("[!] Please report this error in hackerman.tech");
        }

        // Save the error in a file
        try {
            saveError(module, location, error, err);
        } catch (IOException e) {
            System.out.println("[!] ERROR: Failed to save the error to a file: " + e.getMessage());
        }
    }
}

