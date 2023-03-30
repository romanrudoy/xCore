package com.kervand.core.modules.ignore;


import com.babijon.commons.command.CommandInfo;
import com.babijon.commons.command.ICommand;
import org.bukkit.entity.Player;

@CommandInfo(name = "ignore", onlyPlayers = true)
public class IgnoreCommand extends ICommand {

    public IgnoreCommand() {
        super("ignore");
    }

    @Override
    public void execute(Player player, String[] args) {



    }

}
