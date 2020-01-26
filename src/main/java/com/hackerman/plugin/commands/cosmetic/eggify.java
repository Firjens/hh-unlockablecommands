package com.hackerman.plugin.commands.cosmetic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.hackerman.plugin.commandmanager.hasCommand;
import com.hackerman.plugin.recharge.rechargeMain;
import com.hackerman.plugin.utils.checkTarget;
import com.hackerman.plugin.utils.sendWhisper;

import static com.hackerman.plugin.recharge.rechargeMain.setRechargeNow;

public class eggify extends Command
{
    public eggify()
    {
        super(null, Emulator.getTexts().getValue("unlockablecommands.cmd_eggify.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        String commandId = "eggify";

        if (hasCommand.hasCommand(gameClient.getHabbo(), commandId)) {

            if (strings.length >= 2)
            {

                // Check if the first argument is online
                if (!checkTarget.checkTarget(gameClient.getHabbo(), strings[1], "ROOM")) {
                    return true;
                }

                if (!rechargeMain.canUseNow(gameClient.getHabbo(), commandId)) {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.error.wait"), RoomChatMessageBubbles.ALERT);
                    return true;
                }
                setRechargeNow(gameClient.getHabbo(), commandId, 3000);

                Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(strings[1]);

                gameClient.getHabbo().talk(Emulator.getTexts().getValue("unlockablecommands.cmd_eggify.do").replace("%username%", habbo.getHabboInfo().getUsername()));

                Emulator.getThreading().run(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(habbo, 154, 30);
                        habbo.shout(Emulator.getTexts().getValue("unlockablecommands.cmd_eggify.got"));
                    }
                }, 500);

            }
            sendWhisper.sendWhisper(gameClient.getHabbo(),Emulator.getTexts().getValue("unlockablecommands.error.supply_username"));

        }
        return true;
    }
}

