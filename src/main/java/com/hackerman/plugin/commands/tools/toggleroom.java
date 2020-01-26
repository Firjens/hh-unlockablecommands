package com.hackerman.plugin.commands.tools;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;

public class toggleroom extends Command
{
    public toggleroom()
    {
        super("cmd_toggleroom", Emulator.getTexts().getValue("unlockablecommands.cmd_toggleroom.keys").split(";"));
    }

    // TODO
    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

        return true;
    }
}


