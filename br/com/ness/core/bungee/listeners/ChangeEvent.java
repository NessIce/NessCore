package br.com.ness.core.bungee.listeners;

import br.com.ness.core.logger.CoreLogger;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Collection;

public class ChangeEvent implements Listener {

    @EventHandler
    public void registerEvent(ServerConnectEvent e){
        sendCustomData(e.getPlayer(), e.getPlayer().getName(),e.getPlayer().getServer().getAddress().getPort());
    }

    public void sendCustomData(ProxiedPlayer player, String data1, int data2) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();

        if (networkPlayers == null || networkPlayers.isEmpty() ) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("SavePlayer");
        out.writeUTF(data1);

        player.getServer().getInfo().sendData( "fryzzen:channel", out.toByteArray() );
    }
}
