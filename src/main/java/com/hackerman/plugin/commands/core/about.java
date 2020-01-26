package com.hackerman.plugin.commands.core;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.hackerman.plugin.main;

import static com.hackerman.plugin.main.pluginAuthor;

public class about  extends Command {
    public about()
    {
        super(null, new String[] { "uc", "unlockablecommands", "ucmd" });
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        String message = "<b>" + main.pluginName + "</b> version " + main.version + "\n";
        message = message + "Plugin created by " + pluginAuthor + "\r\n";
        message = message + "\r\nMore plugins can be found on <b>Hackerman.tech</b>";


        gameClient.getHabbo().alert(message);
        return true;

    }
}
