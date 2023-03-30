package com.kervand.core.database;

import com.kervand.core.objects.CoreUser;

import java.sql.*;

public class CoreMySQL implements IDatabase {

    private final String host;
    private final int port;
    private final String database;
    private final String userName;
    private final String password;

    private Connection connection;

    public CoreMySQL(String host, int port, String database, String userName, String password) {

        this.host = host;
        this.port = port;
        this.database = database;
        this.userName = userName;
        this.password = password;

        try {
            this.openConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.init();

    }

    private void openConnection() throws SQLException, ClassNotFoundException {

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.userName, this.password);
        }

    }

    public void closeConnection() {
        try {

            if (connection != null && !connection.isClosed())
                connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {

        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(name TEXT NOT NULL UNIQUE, ignoreList TEXT NOT NULL, " +
                "lastRewardDate TEXT, lastRewardLevel TINYINT NOT NULL, reportsConfirmed SMALLINT NOT NULL, playerLevel TINYINT NOT NULL, playerXP DOUBLE NOT NULL)")) {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveUser(CoreUser user) {

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users(name, ignoreList, lastRewardDate, lastRewardLevel, reportsConfirmed, playerLevel, playerXP) VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                "playerLevel=" + user.getLevel() + ", playerXP=" + user.getXp() + ", ignoreList=" + user.getIgnoreList() + ", reportsConfirmed=" + user.getReportsConfirmed() + ", lastRewardDate=" + user.getLastDailyRewardClaimed() +
                ", lastRewardLevel=" + user.getLastDailyRewardLevel())) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getIgnoreListAsString());
            statement.setString(3, user.getLastDailyRewardClaimed());
            statement.setInt(4, user.getLastDailyRewardLevel());
            statement.setInt(5, user.getReportsConfirmed());
            statement.setInt(6, user.getLevel());
            statement.setDouble(7, user.getXp());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public CoreUser loadUserFromDatabase(String user) {

        CoreUser coreUser = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1")) {

            statement.setString(1, user);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    coreUser = new CoreUser(
                            resultSet.getString("name"),
                            resultSet.getString("ignoreList"),
                            resultSet.getInt("lastRewardLevel"),
                            resultSet.getString("lastRewardDate"),
                            resultSet.getInt("reportsConfirmed"),
                            resultSet.getInt("playerLevel"),
                            resultSet.getDouble("playerXP")
                    );
                }

                resultSet.close();
                statement.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return coreUser;

    }

    public boolean isRegistered(String user) {

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1")) {

            statement.setString(1, user);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return true;
                }

                resultSet.close();
                statement.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

}
