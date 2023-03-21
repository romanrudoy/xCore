package com.kervand.core.modules.newbiefilter;

import com.babijon.commons.listener.AbstractListener;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.mrbrikster.chatty.api.events.ChattyMessageEvent;

public class NewbieHandler extends AbstractListener {

    private final CorePlugin plugin;

    private int secondsPlay;
    private Sound noSound;

    public NewbieHandler() {
        this.plugin = CorePlugin.getInstance();
        load();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        if (player.getTicksLived() / 20 < secondsPlay) {
            event.setCancelled(true);

            player.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("newbie-filter.messages.waiting")
                    .replace("<time>", "" + (secondsPlay - player.getTicksLived() / 20))));
            if (noSound != null)
                player.playSound(player.getLocation(), noSound, 1, 1);

        }
    }

    public void load() {
        secondsPlay = plugin.getConfig().getInt("newbie-filter.settings.time-to-chat");
        noSound = Sound.valueOf(plugin.getConfig().getString("newbie-filter.settings.no-sound"));
    }

}
