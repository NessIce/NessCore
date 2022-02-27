package br.com.ness.core.bukkit.npc;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.listeners.core.ClickNPCEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader {


    Channel channel;
    public static Map<UUID, Channel> channels = new HashMap<>();

    public void inject(Player player){
        CraftPlayer craftPlayer = (CraftPlayer)player;

        channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
        channels.put(craftPlayer.getUniqueId(), channel);

        if(channel.pipeline().get("PacketInjector")!=null){
            return;
        }
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channel, Packet<?> packet, List<Object> args) throws Exception {
                args.add(packet);
                readPacket(player, packet);
            }
        });
    }

    public void uninject(Player player){
        channel = channels.get(player.getUniqueId());
        if(channel.pipeline().get("PacketInjector")!=null){
            channel.pipeline().remove("PacketInjector");
        }
    }

    public void readPacket(Player player, Packet<?> packet){
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){

            if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT")){
                return;
            }

            int id = (int)getValue(packet, "a");
            NPCClickType clickType = null;

            if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
                clickType = NPCClickType.LEFT_CLICK;
            }

            if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
                clickType = NPCClickType.RIGHT_CLICK;
            }

            for(NPC npc : NPC.getNPCS()){
                if(npc.getEntityPlayer().getId() == id){
                    NPCClickType finalClickType = clickType;

                    Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getCore(), new Runnable() {
                        @Override
                        public void run() {
                            Bukkit.getPluginManager().callEvent(new ClickNPCEvent(player, npc.getEntityPlayer(), finalClickType));
                        }},0);
                }
            }
        }

    }

    private Object getValue(Object instance, String name){
        Object result = null;
        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);

        }catch (Exception e){
            e.printStackTrace();;
        }

        return result;
    }

}
