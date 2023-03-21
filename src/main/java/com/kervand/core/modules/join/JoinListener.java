package com.kervand.core.modules.join;

import com.babijon.commons.listener.AbstractListener;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinListener extends AbstractListener {

    private final Sound sound;
    private final List<String> motd;
    private final List<String> firstJoin;

    public JoinListener() {
        this.sound = Sound.valueOf(CorePlugin.getInstance().getConfig().getString("join.sound"));
        this.motd = MessageUtil.getColorList(CorePlugin.getInstance().getConfig().getStringList("join.motd"));
        this.firstJoin = MessageUtil.getColorList(CorePlugin.getInstance().getConfig().getStringList("join.new-player"));
    }



    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.setJoinMessage(null);
        if (!Bukkit.getOfflinePlayer(event.getPlayer().getUniqueId()).hasPlayedBefore()) {
            firstJoin.forEach(m -> Bukkit.broadcast(m, ""));
        }

        motd.forEach(m -> event.getPlayer().sendMessage(m));
        if (sound != null)
            event.getPlayer().playSound(event.getPlayer().getLocation(), sound, 1L, 1L);

    }

}
