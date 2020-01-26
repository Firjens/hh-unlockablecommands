package com.hackerman.plugin.commands.cosmetic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnitEffect;
import com.eu.habbo.habbohotel.users.Habbo;
import com.hackerman.plugin.commandmanager.hasCommand;
import com.hackerman.plugin.recharge.rechargeMain;

import static com.hackerman.plugin.recharge.rechargeMain.setRechargeNow;

public class poof extends Command
{
    public poof()
    {
        super(null, Emulator.getTexts().getValue("unlockablecommands.cmd_poof.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        String commandId = "poof";
        if (hasCommand.hasCommand(gameClient.getHabbo(), "poof")) {
            if (!rechargeMain.canUseNow(gameClient.getHabbo(), commandId)) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.error.wait"), RoomChatMessageBubbles.ALERT);
                return true;
            }
            setRechargeNow(gameClient.getHabbo(), commandId, 5000);


            Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
            final Habbo habbo = gameClient.getHabbo();
            if (room != null) {
                gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(habbo, 66, 1);
                final RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getRandomWalkableTile();
                Emulator.getThreading().run(new Runnable() {
                    @Override
                    public void run() {
                        gameClient.getHabbo().talk(Emulator.getTexts().getValue("unlockablecommands.cmd_poof.do"));
                        gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(habbo, RoomUnitEffect.NINJADISAPPEAR.getId(), 1);
                    }
                }, 900);
                Emulator.getThreading().run(new Runnable() {
                    @Override
                    public void run() {
                        gameClient.getHabbo().getRoomUnit().setLocation(tile);
                        gameClient.getHabbo().getRoomUnit().setCurrentLocation(tile);
                        gameClient.getHabbo().getHabboInfo().getCurrentRoom().updateHabbo(gameClient.getHabbo());
                    }
                }, 1600);
            }


        }
        return true;
    }
}