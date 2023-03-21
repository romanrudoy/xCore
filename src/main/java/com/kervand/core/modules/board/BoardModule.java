package com.kervand.core.modules.board;

import com.kervand.core.CorePlugin;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class BoardModule {

    private final List<String> boards = new ArrayList<>();
    private final int changeTime;
    private BukkitTask task;

    public BoardModule() {
        this.boards.addAll(CorePlugin.getInstance().getConfig().getStringList("board.pages"));
        this.changeTime = CorePlugin.getInstance().getConfig().getInt("board.switch-time");
    }

    public void run() {

        task = new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {

                i++;
                if (i >= boards.size()) i = 0;

                Bukkit.getOnlinePlayers().forEach(p -> TabAPI.getInstance().getScoreboardManager()
                        .showScoreboard(TabAPI.getInstance().getPlayer(p.getUniqueId()),
                                TabAPI.getInstance().getScoreboardManager().getRegisteredScoreboards().get(boards.get(i))));

            }

        }.runTaskTimer(CorePlugin.getInstance(), 0L, 20L * changeTime);

    }

    public void stop() {
        task.cancel();
    }

}
