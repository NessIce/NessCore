package br.com.ness.core.bukkit.listeners.events;

import br.com.ness.core.bukkit.npc.PacketReader;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void registerEvent(PlayerQuitEvent e){
        Player player = e.getPlayer();
        e.setQuitMessage(null);

        new PacketReader().uninject(player);

        Profile.removeProfile(player);
    }
}
