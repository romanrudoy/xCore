package com.kervand.core.modules.sound;

import com.kervand.core.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoundContainer {

    private static final Map<UUID, BukkitTask> PLAYING = new HashMap<>();
    private static final Map<UUID, String> CURRENT_SOUND = new HashMap<>();

    public static void playSound(Player player, int duration, int times, String sound) {

        if (PLAYING.containsKey(player.getUniqueId())) {
            PLAYING.remove(player.getUniqueId()).cancel();
            player.stopSound(CURRENT_SOUND.remove(player.getUniqueId()));
        }

        BukkitRunnable runnable = new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {

                if (i >= times) {
                    this.cancel();
                    return;
                }

                player.playSound(player.getLocation(), sound, 1f, 1f);
                i++;

            }

        };

        PLAYING.put(player.getUniqueId(), runnable.runTaskTimer(CorePlugin.getInstance(), 0L, 20L * duration));
        CURRENT_SOUND.put(player.getUniqueId(), sound);

    }

    public static void stopSound(UUID uuid) {

        if (!PLAYING.containsKey(uuid)) return;
        PLAYING.remove(uuid).cancel();

        String sound = CURRENT_SOUND.remove(uuid);
        if (Bukkit.getPlayer(uuid) != null) Bukkit.getPlayer(uuid).stopSound(sound);

    }

}
