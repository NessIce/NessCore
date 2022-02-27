package br.com.ness.core.bukkit;

import br.com.ness.core.bukkit.channelmessage.ChannelMessage;
import br.com.ness.core.bukkit.commands.CommandManager;
import br.com.ness.core.bukkit.groups.Group;
import br.com.ness.core.bukkit.listeners.EventManager;
import br.com.ness.core.bukkit.npc.PacketReader;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.delivery.Delivery;
import br.com.ness.core.bukkit.protocol.Protocol;
import br.com.ness.core.database.AutoBase;
import br.com.ness.core.database.Database;
import br.com.ness.core.logger.CoreLogger;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.event.PluginMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Database.open();
        Database.getAutoBase().createTable("profiles", new String[]{"`player` VARCHAR(24), `data` BLOB"});

        getServer().getMessenger().registerIncomingPluginChannel( this, "fryzzen:channel", new ChannelMessage());

        EventManager.register(this);
        CommandManager.register(this);
        Protocol.register(this);

        Delivery.loadDeliveries();
        Group.loadGroups();

        CoreLogger.SUCESS.sendMessage("ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player->{
            Profile.getByPlayer(player).save();
            new PacketReader().uninject(player);
        });

        Database.close();
        CoreLogger.SEVERE.sendMessage("desativado com sucesso!");
    }

    public static CorePlugin getCore() { return CorePlugin.getPlugin(CorePlugin.class); }


}
