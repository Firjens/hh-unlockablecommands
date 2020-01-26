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

import static com.hackerman.plugin.commandmanager.addCommand.addCommandToDB;
import static com.hackerman.plugin.commandmanager.addCommand.addCommandToOnline;

public class givecommand extends Command
{
    public givecommand()
    {
        super("cmd_givecommand", Emulator.getTexts().getValue("unlockablecommands.cmd_givecommand.keys").split(";"));
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

        // If 4th argument is set (amount of days to give)
        int days = -1;
        if (strings.length == 4) {
            try {
                days = Integer.parseInt(strings[3]);
            } catch (NumberFormatException e) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.error.invalid_days"), RoomChatMessageBubbles.ALERT);
                return true;
            }

            if (days < 0 || days > 9999) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.error.invalid_days"), RoomChatMessageBubbles.ALERT);
                return true;
            }
        }

        String timestamp = null;
        if (days == -1) {
            timestamp = Emulator.getTexts().getValue("unlockablecommands.timestamp_permanent");
        } else {
            timestamp = Emulator.getTexts().getValue("unlockablecommands.timestamp_days").replaceAll("%time%", String.valueOf(days));
        }
        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.give_commands").replace("%command%", commandId).replace("%username%", targetName).replace("%time%", timestamp), RoomChatMessageBubbles.ALERT);

        // GIVE IT! :D
        // If the player already has it it'll be updated
        Habbo HabboOnline = Emulator.getGameEnvironment().getHabboManager().getHabbo(habbo.getId());
        if (HabboOnline != null) {
            addCommandToOnline(HabboOnline, strings[2], days);
            // HabboOnline.whisper(Emulator.getTexts().getValue("unlockablecommands.receive_commands".replace("%command%", commandId).replace("%time%", timestamp)), RoomChatMessageBubbles.ALERT);
        } else {
            addCommandToDB(habbo, strings[2], days);
        }

        return true;
    }
}

