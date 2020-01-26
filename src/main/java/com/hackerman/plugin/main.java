package com.hackerman.plugin;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.users.UserLoginEvent;
import com.hackerman.plugin.commands.admin.givecommand;
import com.hackerman.plugin.commands.admin.removecommand;
import com.hackerman.plugin.commands.core.about;
import com.hackerman.plugin.commands.cosmetic.eggify;
import com.hackerman.plugin.commands.cosmetic.kiss;
import com.hackerman.plugin.commands.cosmetic.poof;
import com.hackerman.plugin.commands.cosmetic.tea;
import com.hackerman.plugin.hhcore.checkIntegrity;
import com.hackerman.plugin.hhcore.log.error;
import com.hackerman.plugin.hhcore.log.generic;
import com.hackerman.plugin.install.registry;
import com.hackerman.plugin.network.addCommand;
import com.hackerman.plugin.profile.loadProfile;

import java.io.IOException;

public class main extends HabboPlugin implements EventListener {
    public static String pluginShort = "unlockable_cmds";
    public static String pluginPrefix = "UNLOCKABLE CMDS";
    public static String pluginName = "Unlockable Commands";
    public static String pluginAuthor = "Hackerman";
    public static String version = "1.0.1";
    public static int productId = 8;

    public void onEnable () throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        System.out.println ( "[~] Detected plugin `" + pluginName + "` by " + pluginAuthor + " version " + version);
    }

    @EventHandler
    public void onEmulatorLoadedEVERYTHING ( EmulatorLoadedEvent e ) throws IOException {
        if (checkIntegrity.checkIntegrity()) {
            Emulator.getPluginManager().registerEvents(this, this);
            long startTime = System.currentTimeMillis();

            // Install
            try {
                registry.load();
                generic.logMessage("Registery -> OK");
            } catch (Exception ex) {
                generic.logInstall("Registery -> ERROR");
                error.logError("loadRegistery","Unlockable Commmands > Emulator Load > Load Register","", 2, false, ex);
            }

            // Load RCON
            Emulator.getRconServer().addRCONMessage("addcommand", addCommand.class);
            // Load Commands
            CommandHandler.addCommand(new about());
            CommandHandler.addCommand(new givecommand());
            CommandHandler.addCommand(new removecommand());
            CommandHandler.addCommand(new eggify());
            CommandHandler.addCommand(new kiss());
            CommandHandler.addCommand(new poof());
            CommandHandler.addCommand(new tea());

            System.out.println("[~] Loaded plugin " + pluginName + " " + version + " in " + (System.currentTimeMillis() - startTime) + "ms -> OK!");
        }
    }

    public void onDisable () throws Exception {
        System.out.println ( "[~] Disabled plugin `" + pluginName + "` by " + pluginAuthor + " version " + version + " -> OK!");
    }

    // Load the user profile on join
    @EventHandler
    public void playerJoin(UserLoginEvent e) {
        loadProfile.loadProfile(e.habbo);
    }

    public boolean hasPermission ( Habbo habbo , String s ) {
        return false;
    }
}
