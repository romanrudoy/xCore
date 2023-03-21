package com.kervand.core.modules;

import com.babijon.commons.listener.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TestListener extends AbstractListener {

    @EventHandler(ignoreCancelled = true)
    public void onLootGenerate(LootGenerateEvent event) {

        // Добавляешь все ресы сюда
        List<ItemStack> loot = new ArrayList<>();

        // ... //

        event.setLoot(loot);

    }
}
