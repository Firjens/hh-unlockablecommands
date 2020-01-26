package com.hackerman.plugin.install;

import com.eu.habbo.Emulator;
import com.hackerman.plugin.hhcore.log.error;
import com.hackerman.plugin.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.hackerman.plugin.hhcore.log.generic.logInstall;
import static com.hackerman.plugin.hhcore.utils.createPermission.registerPermission;

public class registry {
    public static void load() throws Exception {
        boolean createdPerms = false;
        if (!Emulator.getConfig().getValue("unlockablecommands.version").equals(main.version)) {
            if (Emulator.getConfig().getValue("unlockablecommands.version").equals("")) {
                Emulator.getConfig().register("unlockablecommands.version", main.version);
                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                    statement.execute("CREATE TABLE IF NOT EXISTS `users_unlockable_commands` ( `id` int(11) unsigned NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`command_id` varchar(255) NOT NULL, `timestamp_received` int(255) NOT NULL, `timestamp_expire` int(255) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
                    logInstall("Updating table `users_unlockable_commands` -> OK");
                } catch (SQLException ex) {
                    logInstall("Updating table `users_unlockable_commands` -> ERROR");
                    error.logError("installRegistry","Core > Install > 1","", 2, false, ex);
                }

                Emulator.getTexts().register("unlockablecommands.timestamp_permanent","permanent");
                Emulator.getTexts().register("unlockablecommands.timestamp_days","%time% days");

                Emulator.getTexts().register("unlockablecommands.not_unlocked","You do not have this command unlocked, check our website for more information!");
                Emulator.getTexts().register("unlockablecommands.user_offline_notfound","Account %target% not found");
                Emulator.getTexts().register("unlockablecommands.user_onlinenotfound","Player %target% not found");
                Emulator.getTexts().register("unlockablecommands.user_room_notfound","Player %target% not found");
                Emulator.getTexts().register("unlockablecommands.notself","You can't do that on yourself!");

                Emulator.getTexts().register("unlockablecommands.error.supply_username","You must supply an username");
                Emulator.getTexts().register("unlockablecommands.error.command_not_found","Command ID %command% not found");
                Emulator.getTexts().register("unlockablecommands.error.invalid_days","Invalid amount of days: %time%");
                Emulator.getTexts().register("unlockablecommands.error.wait","Please wait until you can use this command again!");

                Emulator.getTexts().register("unlockablecommands.give_commands","Gave command :%command% to %username% for %time%");
                Emulator.getTexts().register("unlockablecommands.remove_commands","Removed command :%command% from %username%");
                Emulator.getTexts().register("unlockablecommands.receive_commands","Command %command% unlocked for %time%");

                // Management Command
                Emulator.getTexts().register("commands.description.cmd_givecommand", ":givecommand");
                Emulator.getTexts().register("unlockablecommands.cmd_givecommand.keys","givecommand;gcmd");
                Emulator.getTexts().register("unlockablecommands.cmd_givecommand.help",":givecommand <player> <command ID> [time in days] - Give command");
                createdPerms = registerPermission("cmd_givecommand", "'0', '1'", "0", createdPerms);

                Emulator.getTexts().register("commands.description.cmd_remcommand", ":removecommand");
                Emulator.getTexts().register("unlockablecommands.cmd_remcommand.keys","removecommand;rcmd");
                Emulator.getTexts().register("unlockablecommands.cmd_remcommand.help",":removecommand <player> <command ID> - Remove command");
                createdPerms = registerPermission("cmd_remcommand", "'0', '1'", "0", createdPerms);

                // Eggify Command
                Emulator.getTexts().register("commands.description.cmd_eggify", ":eggify");
                Emulator.getTexts().register("unlockablecommands.cmd_eggify.keys","eggify;egg");
                Emulator.getTexts().register("unlockablecommands.cmd_eggify.do", "Eggifies %username%");
                Emulator.getTexts().register("unlockablecommands.cmd_eggify.got", ":(");
                createdPerms = registerPermission("cmd_eggify", "'0', '1'", "0", createdPerms);

                // Poof Command
                Emulator.getTexts().register("commands.description.cmd_poof", ":poof");
                Emulator.getTexts().register("unlockablecommands.cmd_poof.keys","poof");
                Emulator.getTexts().register("unlockablecommands.cmd_poof.do", "Poof");
                createdPerms = registerPermission("cmd_poof", "'0', '1'", "0", createdPerms);

                // Kiss Command
                Emulator.getTexts().register("commands.description.cmd_kiss", ":kiss");
                Emulator.getTexts().register("unlockablecommands.cmd_kiss.keys","kiss");
                Emulator.getTexts().register("unlockablecommands.cmd_kiss.kissedperson", "* %kissedperson% gets kissed by %kisser% *");
                Emulator.getTexts().register("unlockablecommands.cmd_kiss.kisser", "* Kisses %kissedperson% passionately *");
                Emulator.getTexts().register("unlockablecommands.cmd_kiss.tofar", "%kissedperson% is too far to kiss!");
                createdPerms = registerPermission("cmd_kiss", "'0', '1'", "0", createdPerms);

                // Tea Command
                Emulator.getTexts().register("commands.description.cmd_tea", ":kiss");
                Emulator.getTexts().register("unlockablecommands.cmd_tea.keys","tea");
                Emulator.getTexts().register("unlockablecommands.cmd_tea.do", "Throws shade, sips tea...");
                createdPerms = registerPermission("cmd_tea", "'0', '1'", "0", createdPerms);

            }
        }
    }
}
