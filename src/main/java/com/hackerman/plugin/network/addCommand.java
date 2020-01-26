package com.hackerman.plugin.network;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.habbohotel.users.HabboManager;
import com.eu.habbo.messages.rcon.RCONMessage;
import com.google.gson.Gson;

import static com.hackerman.plugin.commandmanager.addCommand.addCommandToDB;
import static com.hackerman.plugin.commandmanager.addCommand.addCommandToOnline;
import static com.hackerman.plugin.commandmanager.hasCommand.hasCommandOffline;

public class addCommand extends RCONMessage<addCommand.JSONaddCommand> {

    public addCommand() {
        super(addCommand.JSONaddCommand.class);
    }

    @Override
    public void handle(Gson gson, addCommand.JSONaddCommand object) {
        boolean stopitjohn = true;
        this.status = 2;
        this.message = "Unknown state";

        HabboInfo habbo = HabboManager.getOfflineHabboInfo(object.user_id);
        if (habbo == null) {
            this.status = 4;
            this.message = "User not found";
            stopitjohn = false;
        }
        if (hasCommandOffline(habbo, object.command_id)) {
            this.status = 3;
            this.message = "User already has command";
            stopitjohn = false;
        }
        if (stopitjohn) {
            if (habbo.isOnline()) {
                Habbo habboOnline = Emulator.getGameEnvironment().getHabboManager().getHabbo(object.user_id);
                addCommandToOnline(habboOnline, object.command_id, object.unlock_length);
            } else {
                addCommandToDB(habbo, object.command_id, object.unlock_length);
            }
            this.status = 1;
            this.message = "OK";
        }

    }

    static class JSONaddCommand {
        int user_id;
        String command_id;
        int unlock_length;
    }
}
