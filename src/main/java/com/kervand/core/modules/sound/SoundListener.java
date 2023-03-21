package com.kervand.core.modules.sound;

import com.babijon.commons.listener.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class SoundListener extends AbstractListener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        SoundContainer.stopSound(event.getPlayer().getUniqueId());
    }

}
