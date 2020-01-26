package com.hackerman.plugin.commands.cosmetic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.hackerman.plugin.recharge.rechargeMain;
import com.hackerman.plugin.utils.checkTarget;
import com.hackerman.plugin.commandmanager.hasCommand;
import com.hackerman.plugin.utils.sendWhisper;

import static com.hackerman.plugin.recharge.rechargeMain.setRechargeNow;

public class kiss extends Command
{
    public kiss()
    {
        super(null, Emulator.getTexts().getValue("unlockablecommands.cmd_kiss.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
       String commandId = "kiss";
      if (hasCommand.hasCommand(gameClient.getHabbo(), "kiss")) {
          if (!rechargeMain.canUseNow(gameClient.getHabbo(), commandId)) {
              gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.error.wait"), RoomChatMessageBubbles.ALERT);
              return true;
          }
          setRechargeNow(gameClient.getHabbo(), commandId, 2000);

          if (strings.length >= 2)
          {

              // Check if the first argument is online
              if (!checkTarget.checkTarget(gameClient.getHabbo(), strings[1], "ROOM")) {
                  return true;
              }
              Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(strings[1]);

              if (habbo.getRoomUnit().getCurrentLocation().distance(gameClient.getHabbo().getRoomUnit().getCurrentLocation()) <= 1.5)
              {
                  habbo.getRoomUnit().lookAtPoint(gameClient.getHabbo().getRoomUnit().getCurrentLocation());
                  gameClient.getHabbo().getRoomUnit().lookAtPoint(habbo.getRoomUnit().getCurrentLocation());
                  habbo.talk(Emulator.getTexts().getValue("unlockablecommands.cmd_kiss.kissedperson").replace("%kisser%", gameClient.getHabbo().getHabboInfo().getUsername()).replace("%kissedperson%", habbo.getHabboInfo().getUsername()), RoomChatMessageBubbles.HEARTS);
                  gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(habbo, 9, 2);
                  gameClient.getHabbo().talk(Emulator.getTexts().getValue("unlockablecommands.cmd_kiss.kisser").replace("%kisser%", gameClient.getHabbo().getHabboInfo().getUsername()).replace("%kissedperson%", habbo.getHabboInfo().getUsername()), RoomChatMessageBubbles.HEARTS);
                  return true;
              }
              gameClient.getHabbo().whisper(Emulator.getTexts().getValue("unlockablecommands.cmd_kiss.tofar").replace("%kissedperson%", habbo.getHabboInfo().getUsername()));
              return true;
          }
          sendWhisper.sendWhisper(gameClient.getHabbo(),Emulator.getTexts().getValue("unlockablecommands.error.supply_username"));

      }
        return true;
    }
}
