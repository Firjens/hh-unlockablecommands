package com.hackerman.plugin.commandmanager;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.hackerman.plugin.hhcore.log.error;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class remCommand {
    public static void remCommandOfflineDB(HabboInfo habbo, String command) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users_unlockable_commands WHERE user_id=? AND command_id=?")) {
                statement.setInt(1, habbo.getId());
                statement.setString(2, command);
                statement.executeUpdate();
                statement.close();
                statement.getConnection().close();
            }
        } catch(SQLException e) {
            error.logError("SQL removeCommand(" + habbo.getId() + ") + " + command,"utils > remCommand","", 2, false, e);
        }
    }
    public static void remCommandOffline(Habbo habbo, String command) {
        habbo.getHabboStats().cache.remove("uc_cmd_" + command, "1");
    }
}
