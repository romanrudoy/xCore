package com.kervand.core;

import com.kervand.core.objects.CoreUser;

import java.util.HashMap;
import java.util.Map;

public class CoreManager {

    private final Map<String, CoreUser> loadedUsers = new HashMap<>();
    private final CorePlugin plugin;

    public CoreManager(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public CoreUser getOrLoadUser(String name) {

        if (loadedUsers.containsKey(name))
            return loadedUsers.get(name);

        CoreUser user;
        if (plugin.getCoreDatabase().isRegistered(name)) {
            user = plugin.getCoreDatabase().loadUserFromDatabase(name);
        } else {

            user = new CoreUser(name);
            plugin.getCoreDatabase().saveUserToDatabase(user);

        }

        loadedUsers.put(name, user);
        return user;

    }

    public void saveUser(CoreUser user) {

        loadedUsers.put(user.getName(), user);
        plugin.getCoreDatabase().saveUserToDatabase(user);

    }

}
