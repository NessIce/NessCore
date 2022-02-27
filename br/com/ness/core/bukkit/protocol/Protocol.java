package br.com.ness.core.bukkit.protocol;

import br.com.ness.core.bukkit.CorePlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Protocol {

    public static void register(CorePlugin plugin) {
        List<WrappedGameProfile> hoverMsg = new ArrayList<>();

        List<String> msg = new ArrayList<>();
        msg.add("§6§lFRYZZEN");
        msg.add("");
        msg.add("§fVenha jogar conosco");
        msg.add("§fe divirta-se!");
        msg.add("");
        msg.add("§eloja.fryzzen.com");

        for (String str : msg) {
            hoverMsg.add(new WrappedGameProfile("1", ChatColor.translateAlternateColorCodes('&', str)));
        }

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.NORMAL, Collections.singletonList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
                    public void onPacketSending(PacketEvent event) {
                        hoverMsg.clear();
                        for (String str : msg) {
                            hoverMsg.add(new WrappedGameProfile("1", ChatColor.translateAlternateColorCodes('&', str.replaceAll("%player%", event.getPlayer().getName()))));
                        }
                        (event.getPacket().getServerPings().read(0)).setPlayers(hoverMsg);
                    }
                });
    }
}
