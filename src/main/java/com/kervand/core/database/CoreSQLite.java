package com.kervand.core.database;

import com.kervand.core.objects.CoreUser;

import java.sql.*;

public class CoreSQLite implements IDatabase {

    private final String url;

    private Connection connection;

    public CoreSQLite() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        url = "jdbc:sqlite:plugins/xCore/users.db";
        Class.forName("org.sqlite.JDBC").newInstance();

        this.connection = getConnection();
        this.init();
    }

    private void init() {

        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(name TEXT NOT NULL UNIQUE, ignoreList TEXT NOT NULL, " +
                "lastRewardDate TEXT NOT NULL, lastRewardLevel TINYINT NOT NULL, reportsConfirmed SMALLINT NOT NULL, playerLevel TINYINT NOT NULL, playerXP DOUBLE NOT NULL)")) {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(CoreUser user) {

        try (PreparedStatement statement = connection.prepareStatement("REPLACE INTO users(name, ignoreList, lastRewardDate, lastRewardLevel, reportsConfirmed, playerLevel, playerXP) VALUES (?,?,?,?,?,?,?)")) {

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
