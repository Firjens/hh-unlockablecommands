package com.hackerman.plugin.commands.admin;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.habbohotel.users.HabboManager;
import com.hackerman.plugin.commandmanager.commandExist;
import com.hackerman.plugin.utils.sendWhisper;

import static com.hackerman.plugin.commandmanager.remCommand.remCommandOffline;
import static com.hackerman.plugin.commandmanager.remCommand.remCommandOfflineDB;

public class removecommand extends Command
{
    public removecommand()
    {
        super("cmd_remcommand", Emulator.getTexts().getValue("unlockablecommands.cmd_remcommand.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        if (strings.length < 3)
        {
            sendWhisper.sendWhisper(gameClient.getHabbo(), Emulator.getTexts().getValue("unlockablecommands.cmd_givecommand.help"));
            return true;
        }
        // Data
        String targetName = strings[1];
        String commandId = strings[2];

        // Check if the 2nd ID is an existing player
        HabboInfo habbo = HabboManager.getOfflineHabboInfo(strings[1]);
        if (habbo == null) {
            sendWhisper.sendWhisper(gameClient.getHabbo(), Emulator.getTexts().getValue("unlockablecommands.user_offline_notfound".replace("%target%", strings[1])));
            return true;
        }

        // Check if 3rd string is a command ID
        if (!commandExist.checkIfCommandExists(strings[2])) {
            sendWhisper.sendWhisper(gameClient.getHabbo(), Emulator.getTexts().getValue("unlockablecommands.error.command_not_found".replace("%command%", strings[2])));
            return true;
        }

        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.remove_commands").replace("%command%", commandId).replace("%username%", targetName), RoomChatMessageBubbles.ALERT);

        Habbo HabboOnline = Emulator.getGameEnvironment().getHabboManager().getHabbo(habbo.getId());
        if (HabboOnline != null) {
            remCommandOffline(HabboOnline, strings[2]);
        } else {
            remCommandOfflineDB(habbo, strings[2]);
        }

        return true;
    }
}


