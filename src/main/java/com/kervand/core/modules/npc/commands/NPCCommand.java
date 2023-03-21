package com.kervand.core.modules.npc.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import com.kervand.core.modules.npc.NPCModule;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("npcs|npc")
public class NPCCommand extends BaseCommand {

    private final CorePlugin plugin = CorePlugin.getInstance();
    private final NPCModule module;

    public NPCCommand() {
        this.module = plugin.getNpcModule();
    }

    @HelpCommand
    private void help(CommandSender sender) {

    }

    @Subcommand("sethelmet")
    private void setHelmet(CommandSender sender, String[] args) {

        if (!sender.hasPermission("xcore.admin")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCommand only for players.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("§e/npc sethelmet <npc key>");
            return;
        }

        String key = args[0];
        if (!module.getNpcConfig().get().getKeys(false).contains(key)) {
            sender.sendMessage("§cNPC " + key + " not found.");
            return;
        }

        Player player = (Player) sender;
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must hold item in hand.");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        module.getNpcConfig().get().set(key + ".helmet", item);
        module.getNpcConfig().save();

        module.reload();

    }

    @Subcommand("setoffhand")
    private void setOffHand(CommandSender sender, String[] args) {

        if (!sender.hasPermission("xcore.admin")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCommand only for players.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("§e/npc sethelmet <npc key>");
            return;
        }

        String key = args[0];
        if (!module.getNpcConfig().get().getKeys(false).contains(key)) {
            sender.sendMessage("§cNPC " + key + " not found.");
            return;
        }

        Player player = (Player) sender;
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must hold item in hand.");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        module.getNpcConfig().get().set(key + ".off-hand", item);
        module.getNpcConfig().save();

        module.reload();

    }

    @Subcommand("setmainhand")
    private void setMainHand(CommandSender sender, String[] args) {

        if (!sender.hasPermission("xcore.admin")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCommand only for players.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("§e/npc sethelmet <npc key>");
            return;
        }

        String key = args[0];
        if (!module.getNpcConfig().get().getKeys(false).contains(key)) {
            sender.sendMessage("§cNPC " + key + " not found.");
            return;
        }

        Player player = (Player) sender;
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must hold item in hand.");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        module.getNpcConfig().get().set(key + ".main-hand", item);
        module.getNpcConfig().save();

        module.reload();

    }

    @Subcommand("reload")
    private void reload(CommandSender sender) {

        if (!sender.hasPermission("xcore.admin")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        plugin.getNpcModule().reload();
        sender.sendMessage("§aNPCs reloaded.");

    }

}
