package com.kervand.core.modules.sound.commands;

import com.babijon.commons.command.CommandInfo;
import com.babijon.commons.command.ICommand;
import com.babijon.commons.utils.MessageUtil;
import com.babijon.commons.utils.NumberUtils;
import com.kervand.core.CorePlugin;
import com.kervand.core.modules.sound.SoundContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.OptionalInt;

@CommandInfo(name = "soundloop", aliases = {"sl", "sloop"})
public class SoundCommand extends ICommand {

    private final CorePlugin plugin = CorePlugin.getInstance();

    public SoundCommand() {
        super("soundloop");
    }

    @Override
    public void execute(CommandSender issuer, String[] args) {

        if (!issuer.hasPermission("core.admin")) {
            issuer.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        if (args.length < 4) {
            issuer.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.soundloop-usage")));
            return;
        }

        String playerName = args[0];
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            issuer.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("player-null")));
            return;
        }

        String sound = args[1];
        OptionalInt durationOpt = NumberUtils.parseInt(args[2]);
        OptionalInt timesToPlayOpt = NumberUtils.parseInt(args[3]);

        if (!durationOpt.isPresent()) {
            player.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.arg-unknown")));
            return;
        }

        if (!timesToPlayOpt.isPresent()) {
            player.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.arg-unknown")));
            return;
        }

        int duration = durationOpt.getAsInt();
        int timesToPlay = timesToPlayOpt.getAsInt();

        SoundContainer.playSound(player, duration, timesToPlay, sound);
        issuer.sendMessage(MessageUtil.colorize(String.format(plugin.getConfig().getString("messages.playsound"), player.getName())));

    }

}
