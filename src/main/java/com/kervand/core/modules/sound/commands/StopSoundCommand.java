package com.kervand.core.modules.sound.commands;

import com.babijon.commons.command.CommandInfo;
import com.babijon.commons.command.ICommand;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import com.kervand.core.modules.sound.SoundContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


@CommandInfo(name = "stopsound")
public class StopSoundCommand extends ICommand {

    private final CorePlugin plugin = CorePlugin.getInstance();

    public StopSoundCommand() {
        super("stopsound");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("core.admin")) {
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
