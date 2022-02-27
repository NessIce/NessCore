package br.com.ness.core.bukkit.listeners.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdEvent implements Listener {

    @EventHandler
    public void registerEvent(ServerListPingEvent e){
        String line1 = "           §6§lFRYZZEN §7[1.8.x - 1.16.x]";
        String line2 = "§eUma batalha nas alturas, acesse: §bloja.fryzzen.com";

        e.setMotd(line1+"\n"+line2);
    }
}
