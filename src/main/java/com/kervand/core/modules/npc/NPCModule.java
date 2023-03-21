package com.kervand.core.modules.npc;

import com.babijon.commons.config.Config;
import com.babijon.commons.npc.NPCService;
import com.babijon.commons.npc.containers.NPCOptions;
import com.babijon.commons.utils.LocationUtil;
import com.babijon.commons.utils.MessageUtil;
import com.kervand.core.CorePlugin;
import com.kervand.core.objects.NPC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NPCModule {

    private final @Getter Config npcConfig;
    private final @Getter NPCService npcService = new NPCService(CorePlugin.getInstance(), true);
    private final List<NPC> npcList = new ArrayList<>();

    public NPCModule() {
        npcConfig = new Config(CorePlugin.getInstance(), "npcs.yml");
        loadNPCs();
    }

    private void loadNPCs() {

        npcList.clear();
        for (String key : npcConfig.get().getKeys(false)) {

            String name = MessageUtil.colorize(npcConfig.get().getString(key + ".name"));
            String skinTexture = npcConfig.get().getString(key + ".skin-texture");
            String skinSignature = npcConfig.get().getString(key + ".skin-signature");
            String command = npcConfig.get().getString(key + ".command");
            Location location = LocationUtil.parseLocation(npcConfig.get().getString(key + ".location"), true);

            ItemStack helmet = new ItemStack(Material.AIR);
            if (npcConfig.get().getConfigurationSection(key).getKeys(false).contains("helmet")) helmet = npcConfig.get().getItemStack(key + ".helmet");

            ItemStack mainHand = new ItemStack(Material.AIR);
            if (npcConfig.get().getConfigurationSection(key).getKeys(false).contains("main-hand")) mainHand = npcConfig.get().getItemStack(key + ".main-hand");

            ItemStack offHand = new ItemStack(Material.AIR);
            if (npcConfig.get().getConfigurationSection(key).getKeys(false).contains("off-hand")) offHand = npcConfig.get().getItemStack(key + ".off-hand");

            npcList.add(new NPC(
                    npcService.newNPC(NPCOptions.builder()
                            .name(name)
                            .hideNametag(false)
                            .location(location)
                            .texture(skinTexture)
                            .signature(skinSignature)
                            .rotateHead(true)
                            .mainHand(mainHand)
                            .helmet(helmet)
                            .offHand(offHand)
                            .build()), command, npcConfig.get().getInt(key + ".command-cooldown"), MessageUtil.colorize(npcConfig.get().getString(key + ".command-cooldown-message")))
            );

        }

    }

    public void show(Player player) {
        npcList.forEach(n -> n.getNpc().showTo(player));
    }

    public void hide(Player player) {
        npcList.forEach(n -> n.getNpc().hideFrom(player));
    }

    public void clear() {
        npcList.forEach(n -> n.getNpc().delete());
    }

    public void refresh(Player player) {
        npcList.forEach(n -> {
            n.getNpc().hideFrom(player);
            n.getNpc().showTo(player);
        });
    }

    public void reload() {

        clear();

        npcConfig.reload();
        loadNPCs();

        Bukkit.getOnlinePlayers().forEach(this::show);

    }

    public NPC getNPC(com.babijon.commons.npc.containers.interfaces.NPC npc) {
        List<NPC> options = npcList.stream().filter(n -> n.getNpc().equals(npc)).collect(Collectors.toList());
        return options.get(0);
    }

}
