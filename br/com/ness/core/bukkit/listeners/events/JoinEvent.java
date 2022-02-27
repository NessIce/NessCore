package br.com.ness.core.bukkit.listeners.events;

import br.com.ness.core.bukkit.listeners.core.CoreJoinEvent;
import br.com.ness.core.bukkit.nms.NMS;
import br.com.ness.core.bukkit.npc.NPC;
import br.com.ness.core.bukkit.npc.PacketReader;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.utils.SerializerCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Collections;
import java.util.Date;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void registerEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.setJoinMessage(null);

        NPC.showNPC(Collections.singletonList(player), NPC.getNPCS());
        new PacketReader().inject(player);

        Profile profile = new Profile(player);
        profile.load();
        Profile.addProfile(player, profile);

        profile.set("geral", "lastLogin", SerializerCore.getDate(new Date()));

        CoreJoinEvent coreJoinEvent = new CoreJoinEvent(player, profile);
        Bukkit.getPluginManager().callEvent(coreJoinEvent);

        NMS.sendTablist(player);
    }
}
