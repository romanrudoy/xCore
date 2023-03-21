package com.kervand.core.modules.npc.listeners;

import com.babijon.commons.cooldown.Cooldowns;
import com.babijon.commons.listener.AbstractListener;
import com.babijon.commons.npc.containers.enums.NPCClickAction;
import com.babijon.commons.npc.events.NPCInteractionEvent;
import com.kervand.core.CorePlugin;
import com.kervand.core.objects.NPC;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class NPCListener extends AbstractListener {

    @EventHandler(ignoreCancelled = true)
    public void onNPCInteraction(NPCInteractionEvent event) {

        NPC npc = CorePlugin.getInstance().getNpcModule().getNPC(event.getClicked());

        if (npc.getCommand() == null) return;
        if (event.getClickAction() != NPCClickAction.INTERACT_AT) return;

        Player player = event.getPlayer();
        String left = Cooldowns.getLeft(player, "npc_" + event.getClicked().getId());
        if (left != null) {
            player.sendMessage(npc.getCooldownMessage());
            return;
        }

        Bukkit.dispatchCommand(player, PlaceholderAPI.setPlaceholders(player, npc.getCommand()));
        Cooldowns.addCooldown(player, "npc_" + event.getClicked().getId(), npc.getCooldown());

    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        CorePlugin.getInstance().getNpcModule().show(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        CorePlugin.getInstance().getNpcModule().refresh(event.getPlayer());
    }

}
