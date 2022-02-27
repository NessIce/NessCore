package br.com.ness.core.bukkit.commands;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.commands.list.essentials.FlyCommand;
import br.com.ness.core.bukkit.commands.list.FriendCommand;
import br.com.ness.core.bukkit.commands.list.essentials.GameModeCommand;
import br.com.ness.core.bukkit.commands.list.group.GroupCommand;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

public class CommandManager {

    public static void register(CorePlugin plugin){
        CommandMap map = ((CraftServer)plugin.getServer()).getCommandMap();
        map.register("friend", new FriendCommand("friend"));
        map.register("fly", new FlyCommand("fly"));
        map.register("group", new GroupCommand("group"));
        map.register("gamemode", new GameModeCommand("gamemode"));
    }
}
