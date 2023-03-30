package com.kervand.core;

import com.kervand.core.database.CoreMySQL;
import com.kervand.core.database.CoreSQLite;
import com.kervand.core.database.IDatabase;
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

import java.sql.SQLException;

public class CorePlugin extends JavaPlugin {

    private static @Getter CorePlugin instance;

    private @Getter BoardModule boardModule;
    private @Getter NPCModule npcModule;
    private @Getter NewbieHandler newbieHandler;

    private @Getter IDatabase coreDatabase;
    private @Getter CoreManager coreManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        saveResource("npcs.yml", false);

        new SoundCommand();
        new StopSoundCommand();

        new CorePlaceholders().register();

        boardModule = new BoardModule();
        if (getConfig().getBoolean("modules.board")) {
            boardModule.run();
        }

        if (getConfig().getBoolean("modules.npc")) {

            npcModule = new NPCModule();

            new NPCCommand();
            new NPCListener();

        }

        if (getConfig().getBoolean("modules.join"))
            new JoinListener();

        if (getConfig().getBoolean("modules.newbie"))
            newbieHandler = new NewbieHandler();

        if (getConfig().getString("use-database").equalsIgnoreCase("MYSQL")) {
            this.coreDatabase = new CoreMySQL(getConfig().getString("database.host"), getConfig().getInt("database.port"),
                    getConfig().getString("database.database"), getConfig().getString("database.user"),
                    getConfig().getString("database.password"));
        } else {
            try {
                this.coreDatabase = new CoreSQLite();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        this.coreManager = new CoreManager(this);

    }

    @Override
    public void onDisable() {

        boardModule.stop();
        coreDatabase.closeConnection();

    }

}
