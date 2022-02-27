package br.com.ness.core.bukkit.listeners;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.listeners.events.*;

public class EventManager {
    public static void register(CorePlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new InteractEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new JoinEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new QuitEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MotdEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ClickEvent(), plugin);
    }
}
