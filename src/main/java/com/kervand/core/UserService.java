package com.kervand.core;

import com.kervand.core.objects.CoreUser;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private final static Map<String, CoreUser> userMap = new HashMap<>();

    public static CoreUser getUser(String name) {

        if (userMap.containsKey(name)) {
            return userMap.get(name);
        }

        if (!CorePlugin.getInstance().getCoreDatabase().isRegistered(name)) {
            return new CoreUser(name);
        } else {

            userMap.put(name, CorePlugin.getInstance().getCoreDatabase().loadUserFromDatabase(name));
            return userMap.get(name);

        }

    }

    public static void saveUser(CoreUser user) {
        userMap.put(user.getName(), user);
    }

    public static void run() {

        new BukkitRunnable() {

            @Override
            public void run() {
                userMap.values().forEach(v -> CorePlugin.getInstance().getCoreDatabase().saveUser(v));
            }

        }.runTaskTimer(CorePlugin.getInstance(), 0L, 20L*60*5);

    }

}
