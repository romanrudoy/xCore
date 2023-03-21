package com.kervand.core.modules.sound.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import com.kervand.core.modules.sound.SoundContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopSoundCommand extends BaseCommand {

    private final CorePlugin plugin = CorePlugin.getInstance();

    @CommandAlias("stopsound|soundstop")
    private void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("xcore.admin")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.stopsound-usage")));
            return;
        }

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.player-null")));
            return;
        }

        SoundContainer.stopSound(player.getUniqueId());
        sender.sendMessage(MessageUtil.colorize(String.format(plugin.getConfig().getString("messages.stopsound"), player.getName())));

    }

}
