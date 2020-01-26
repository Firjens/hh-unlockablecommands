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

public class addCommand {
    public static void setCommandChange(HabboInfo habbo, String command, String command2, String command3) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE users_unlockable_commands SET " + command2 + "=?  WHERE user_id=? AND command_id=?)")) {
                statement.setInt(1, habbo.getId());
                statement.setString(2, command);
                statement.setString(3, command3);
                statement.executeUpdate();
                statement.close();
                statement.getConnection().close();
            }
        } catch(SQLException e) {
            error.logError("SQL setCommandChange(" + habbo.getId() + ") + " + command + " + " + command2 + " + " + command3,"utils > addCommand > SQL setCommandChange","", 2, false, e);
        }
    }
    public static void addCommandChange(HabboInfo habbo, String command, int length) {
        long unixTime = System.currentTimeMillis() / 1000L;
        long expire = -1;
        if (length > 0) {
            expire = System.currentTimeMillis() / 1000L + length*86400;
        }
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users_unlockable_commands (`user_id`, `command_id`, `timestamp_received`, `timestamp_expire`) VALUES (?, ?, ?, ?)")) {
                statement.setInt(1, habbo.getId());
                statement.setString(2, command);
                statement.setLong(3, unixTime);
                statement.setLong(4, expire);
                statement.executeUpdate();
                statement.close();
                statement.getConnection().close();
            }
        } catch(SQLException e) {
            error.logError("SQL addCommandChange(" + habbo.getId() + ") + " + command,"utils > addCommand > SQL setCommandChange","", 2, false, e);
        }
    }
    public static void addCommandToDB(HabboInfo habbo, String command, int length) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users_unlockable_commands WHERE user_id=? AND command_id=?")) {
                statement.setInt(1, habbo.getId());
                statement.setString(2, command);
                ResultSet rs = statement.executeQuery();

                if (!rs.next()) {
                    addCommandChange(habbo, command, length);
                } else {
                    if (rs.next()) {
                        // If current != permanent and length = permanent, set to permanent
                        if (length < 0 && rs.getInt("timestamp_expire") != -1) {
                            setCommandChange(habbo, command, "timestamp_expire", "-1");
                        }
                        // If length is shorter than expire update it
                        if (length > rs.getInt("timestamp_expire")) {
                            setCommandChange(habbo, command, "timestamp_expire", String.valueOf(length));
                        }
                    }
                }
                rs.close();
                statement.getConnection().close();
            }
        } catch(SQLException e) {
            error.logError("SQL addCommand(" + habbo.getId() + ") + " + command + " + " + length,"utils > addCommandToDB","", 2, false, e);
        }
    }

    public static void addCommandToOnline(Habbo habbo, String command, int length) {
        habbo.getHabboStats().cache.put("uc_cmd_" + command, "1");
        addCommandToDB(habbo.getHabboInfo(), command, length);

    }


}
