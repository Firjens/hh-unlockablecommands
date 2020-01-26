package com.hackerman.plugin.commands.cosmetic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserHandItemComposer;
import com.hackerman.plugin.commandmanager.hasCommand;
import com.hackerman.plugin.recharge.rechargeMain;

import static com.hackerman.plugin.recharge.rechargeMain.setRechargeNow;

public class tea extends Command
{
    public tea()
    {
        super(null, Emulator.getTexts().getValue("unlockablecommands.cmd_tea.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        String commandId = "tea";
        if (hasCommand.hasCommand(gameClient.getHabbo(), "tea")) {
            if (!rechargeMain.canUseNow(gameClient.getHabbo(), commandId)) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.error.wait"), RoomChatMessageBubbles.ALERT);
                return true;
            }
            setRechargeNow(gameClient.getHabbo(), commandId, 2500);

            gameClient.getHabbo().shout("* "+Emulator.getTexts().getValue("unlockablecommands.cmd_tea.do")+" *");
            gameClient.getHabbo().getRoomUnit().setHandItem(10);
            gameClient.getHabbo().getHabboInfo().getCurrentRoom().sendComposer((new RoomUserHandItemComposer(gameClient.getHabbo().getRoomUnit())).compose());
        }
        return true;
    }
}

