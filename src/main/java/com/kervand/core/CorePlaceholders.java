package com.kervand.core;

import com.babijon.commons.utils.MessageUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CorePlaceholders extends PlaceholderExpansion {

    private final CorePlugin plugin;

    public CorePlaceholders() {
        this.plugin = CorePlugin.getInstance();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "xcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "kervand";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

        if (!plugin.getConfig().getConfigurationSection("prefixes").getKeys(false).contains(params)) {
            return "Unknown Prefix";
        } else {
            return MessageUtil.colorize(plugin.getConfig().getString("prefixes." + params));
        }

    }
}
