package com.kervand.core.database;

import com.kervand.core.objects.CoreUser;

import java.sql.*;

public class CoreSQLite implements IDatabase {

    private final String url;

    public CoreSQLite() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        url = "jdbc:sqlite:plugins/xCore/users.db";
        Class.forName("org.sqlite.JDBC").newInstance();

        this.init();
    }

    private void init() {

        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(name TEXT NOT NULL UNIQUE, ignoreList TEXT NOT NULL, suffix TEXT, " +
                "lastRewardDate TEXT, lastRewardLevel TINYINT NOT NULL, reportsConfirmed SMALLINT NOT NULL, playerLevel TINYINT NOT NULL, playerXP DOUBLE NOT NULL)")) {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void closeConnection() {

    }

    public void saveUser(CoreUser user) {

        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("REPLACE INTO users(name, ignoreList, suffix, lastRewardDate, lastRewardLevel, reportsConfirmed, playerLevel, playerXP) VALUES (?,?,?,?,?,?,?,?)")) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getIgnoreListAsString());
            statement.setString(3, user.getSuffix());
            statement.setString(4, user.getLastDailyRewardClaimed());
            statement.setInt(5, user.getLastDailyRewardLevel());
            statement.setInt(6, user.getReportsConfirmed());
            statement.setInt(7, user.getLevel());
            statement.setDouble(8, user.getXp());

            statement.execute();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public CoreUser loadUserFromDatabase(String user) {

        CoreUser coreUser = null;
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1")) {

            statement.setString(1, user);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    coreUser = new CoreUser(
                            resultSet.getString("name"),
                            resultSet.getString("ignoreList"),
                            resultSet.getString("suffix"),
                            resultSet.getInt("lastRewardLevel"),
                            resultSet.getString("lastRewardDate"),
                            resultSet.getInt("reportsConfirmed"),
                            resultSet.getInt("playerLevel"),
                            resultSet.getDouble("playerXP")
                    );
                }

                resultSet.close();
                statement.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return coreUser;

    }

    public boolean isRegistered(String user) {

        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? LIMIT 1")) {

            statement.setString(1, user);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return true;
                }

                resultSet.close();
                statement.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

}
