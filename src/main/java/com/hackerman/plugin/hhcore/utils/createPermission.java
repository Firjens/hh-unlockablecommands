package com.hackerman.plugin.hhcore.utils;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class createPermission {
    public static boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn)
    {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name +"` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'"))
            {
                statement.execute();
                return true;
            }
        } catch (SQLException ignored){}

        return defaultReturn;
    }
}
