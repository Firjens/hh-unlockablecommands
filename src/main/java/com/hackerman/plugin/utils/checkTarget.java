package com.hackerman.plugin.utils;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;

public class checkTarget {

    public static boolean checkTarget(Habbo habbo, String target, String loc) {
        Habbo habtarget = null;

        if (loc.equalsIgnoreCase("ROOM")) {
            habtarget = habbo.getHabboInfo().getCurrentRoom().getHabbo(target);
        }

        if (habtarget == null){
            sendWhisper.sendWhisper(habbo, Emulator.getTexts().getValue("unlockablecommands.user_room_notfound").replace("%t", target));
            return false;
        }
        if (habtarget == habbo){
            sendWhisper.sendWhisper(habbo, Emulator.getTexts().getValue("unlockablecommands.notself"));
            return false;
        }

        return true;
    }
}
