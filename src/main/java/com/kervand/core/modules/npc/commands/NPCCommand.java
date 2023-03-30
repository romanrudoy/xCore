package com.kervand.core.modules.npc.commands;

import com.babijon.commons.command.CommandInfo;
import com.babijon.commons.command.ICommand;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import com.kervand.core.modules.npc.NPCModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandInfo(name = "npc", aliases = { "npcs" }, onlyPlayers = true)
public class NPCCommand extends ICommand {

    private final CorePlugin plugin = CorePlugin.getInstance();
    private final NPCModule npcModule;

    public NPCCommand() {
        super("npc");
        this.npcModule = plugin.getNpcModule();
    }

    @Override
    public void execute(Player player, String[] args) {

        if (!player.hasPermission("core.admin")) {
            player.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.no-permission")));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.npc-usage")));
            return;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "reload":
                reload(player, args);
                break;
            case "sethelmet":
                setHelmet(player, args);
                break;
            case "setmainhand":
                setMainHand(player, args);
                break;
            case "setoffhand":
                setOffHand(player, args);
                break;
            default:
                help(player, args);
                break;
        }

    }

    private void setHelmet(Player player, String[] args) {

        if (args.length == 1) {
            player.sendMessage("§e/npc sethelmet <npc key>");
            return;
        }

        String key = args[1];
        if (!npcModule.getNpcConfig().get().getKeys(false).contains(key)) {
            player.sendMessage("§cNPC " + key + " not found.");
            return;
        }

        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must hold item in hand.");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        npcModule.getNpcConfig().get().set(key + ".helmet", item);
        npcModule.getNpcConfig().save();

        npcModule.reload();

    }

    private void setMainHand(Player player, String[] args) {

        if (args.length == 1) {
            player.sendMessage("§e/npc setmainhand <npc key>");
            return;
        }

        String key = args[1];
        if (!npcModule.getNpcConfig().get().getKeys(false).contains(key)) {
            player.sendMessage("§cNPC " + key + " not found.");
            return;
        }

        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must hold item in hand.");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        npcModule.getNpcConfig().get().set(key + ".main-hand", item);
        npcModule.getNpcConfig().save();

        npcModule.reload();

    }

    private void setOffHand(Player player, String[] args) {

        if (args.length == 1) {
            player.sendMessage("§e/npc setoffhand <npc key>");
            return;
        }

        String key = args[1];
        if (!npcModule.getNpcConfig().get().getKeys(false).contains(key)) {
            player.sendMessage("§cNPC " + key + " not found.");
            return;
        }

        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage("§cYou must hold item in hand.");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        npcModule.getNpcConfig().get().set(key + ".off-hand", item);
        npcModule.getNpcConfig().save();

        npcModule.reload();

    }

    private void help(Player player, String[] args) {
        player.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("messages.npc-usage")));
    }

    private void reload(Player player, String[] args) {
        plugin.getNpcModule().reload();
        player.sendMessage("§aNPCs reloaded.");
    }

}
