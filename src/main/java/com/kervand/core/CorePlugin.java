package com.kervand.core;

import co.aikar.commands.PaperCommandManager;
import com.babijon.commons.xAPI;
import com.kervand.core.modules.board.BoardModule;
import com.kervand.core.modules.join.JoinListener;
import com.kervand.core.modules.newbiefilter.NewbieHandler;
import com.kervand.core.modules.npc.NPCModule;
import com.kervand.core.modules.npc.commands.NPCCommand;
import com.kervand.core.modules.npc.listeners.NPCListener;
import com.kervand.core.modules.sound.commands.SoundCommand;
import com.kervand.core.modules.sound.commands.StopSoundCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    private static @Getter CorePlugin instance;

    private @Getter BoardModule boardModule;
    private @Getter NPCModule npcModule;
    private @Getter NewbieHandler newbieHandler;

    private @Getter CoreDatabase coreDatabase;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        saveResource("npcs.yml", false);

        PaperCommandManager commandManager = new PaperCommandManager(xAPI.getInstance());

        commandManager.registerCommand(new SoundCommand());
        commandManager.registerCommand(new StopSoundCommand());

        new CorePlaceholders().register();

        boardModule = new BoardModule();
        if (getConfig().getBoolean("modules.board")) {
            boardModule.run();
        }

        if (getConfig().getBoolean("modules.npc")) {

            npcModule = new NPCModule();

            commandManager.registerCommand(new NPCCommand());
            new NPCListener();

        }

        if (getConfig().getBoolean("modules.join"))
            new JoinListener();

        if (getConfig().getBoolean("modules.newbie"))
            newbieHandler = new NewbieHandler();

        this.coreDatabase = new CoreDatabase(getConfig().getString("database.host"), getConfig().getInt("database.port"),
                getConfig().getString("database.database"), getConfig().getString("database.user"),
                getConfig().getString("database.password"));

    }

    @Override
    public void onDisable() {

        boardModule.stop();

        coreDatabase.closeConnection();

    }

}