package com.hackerman.plugin.utils;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

public class sendWhisper {
    public static void sendWhisper(Habbo habbo, String message){
        habbo.whisper(message, RoomChatMessageBubbles.ALERT);
    }
}
