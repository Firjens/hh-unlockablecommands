package com.hackerman.plugin.commandmanager;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.hackerman.plugin.hhcore.log.error;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hasCommand {

    public static boolean hasCommandOffline(HabboInfo habbo, String command) {

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT command_id FROM users_unlockable_commands WHERE user_id=? AND command_id=? AND (timestamp_expire < UNIX_TIMESTAMP() OR timestamp_expire = '-1')")) {
                statement.setInt(1, habbo.getId());
                statement.setString(2, command);
                ResultSet rs = statement.executeQuery();

               if (rs.next()) {
                    return true;
                }

                rs.close();
                statement.getConnection().close();
            }
        } catch(SQLException e) {
            error.logError("SQL hasCommand(" + habbo.getId() + ")","utils > hasCommandOffline > SQL get","", 2, false, e);
        }


        return false;
    }

    public static boolean hasCommand(Habbo habbo, String commandId){

        if (habbo.hasPermission("cmd_" + commandId)) {
            return true;
        }
        if (habbo.getHabboStats().cache.get("uc_cmd_" + commandId) != null) {
            return true;
        }

        habbo.whisper(Emulator.getTexts().getValue("unlockablecommands.not_unlocked"), RoomChatMessageBubbles.ALERT);
        return false;
    }
}
