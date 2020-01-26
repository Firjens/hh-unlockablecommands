package com.hackerman.plugin.profile;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.hackerman.plugin.hhcore.log.error;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loadProfile {
    public static void loadProfile(Habbo habbo) {

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT command_id FROM users_unlockable_commands WHERE user_id=? AND (timestamp_expire > UNIX_TIMESTAMP() OR timestamp_expire = '-1')")) {
                statement.setInt(1, habbo.getHabboInfo().getId());
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    habbo.getHabboStats().cache.put("uc_cmd_" + rs.getString("command_id"), "1");
                }

                rs.close();
                statement.getConnection().close();
            }
        } catch(SQLException e) {
            error.logError("SQL loadProfile(" + habbo.getHabboInfo().getId() + ")","loadUserProfile > SQL get unlockable commands","", 2, false, e);
        }
    }

}
