package com.kervand.core.database;

import com.kervand.core.objects.CoreUser;

import java.sql.Connection;

public interface IDatabase {

    void saveUser(CoreUser user);
    CoreUser loadUserFromDatabase(String name);
    boolean isRegistered(String name);

    void closeConnection();

}
