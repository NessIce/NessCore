package br.com.ness.core.bukkit.channelmessage;

import br.com.ness.core.bukkit.profile.Profile;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChannelMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase( "fryzzen:channel")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase( "SavePlayer")){
            String playerName = in.readUTF();

            Profile.getByPlayer(player).save();
        }
    }
}
