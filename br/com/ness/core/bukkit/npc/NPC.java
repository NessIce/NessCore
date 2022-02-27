package br.com.ness.core.bukkit.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class NPC {

    private static final Map<String, NPC> NPCS = new HashMap<>();
    public static List<NPC> getNPCS(){ return new ArrayList<>(NPCS.values()); }
    public static void showNPC(List<Player> player, List<NPC> npcs){
        for(NPC npc : npcs){
            for(Player players : player){
                DataWatcher watcher = npc.getEntityPlayer().getDataWatcher();
                watcher.watch(10, (byte) 127);

                PlayerConnection connection = ((CraftPlayer)players).getHandle().playerConnection;

                connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getEntityPlayer().getId(), watcher, true));
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc.getEntityPlayer()));
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc.getEntityPlayer()));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc.getEntityPlayer()));
                connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc.entityPlayer, (byte)(npc.getEntityPlayer().yaw *265/360)));

            }
        }
    }

    private String name;
    private String metadata;
    private String texture;
    private String signature;
    private Location location;
    private EntityPlayer entityPlayer;

    public NPC(String name, String metadata, Location location, String texture, String signature){
      this.name = name;
      this.metadata = metadata;
      this.location = location;
      this.texture = texture;
      this.signature = signature;
    }

    public NPC(String name, String metadata, Location location, String skin){
        this.name = name;
        this.metadata = metadata;
        this.location = location;
        String[] skinProp = getSkin(skin);

        this.texture = skinProp[0];
        this.signature = skinProp[1];
    }

    public void spawn(){
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        entityPlayer = new EntityPlayer(minecraftServer, world, gameProfile, new PlayerInteractManager(world));

        entityPlayer.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());

        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        showNPC(new ArrayList<>(Bukkit.getOnlinePlayers()), Collections.singletonList(this));
        NPCS.put(name, this);
    }

    public void despawn(Player player){
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(getEntityPlayer().getId()));
        NPCS.remove(name);
    }

    public void despawn(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityDestroy(getEntityPlayer().getId()));
            NPCS.remove(name);
        });
    }

    public static String[] getSkin(String name){
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/"+name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2= new URL("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid+"?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[]{texture, signature};

        }catch (Exception e){
            String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyMjk4NjE2NjUyMCwKICAicHJvZmlsZUlkIiA6ICI0MzE4ZjFjODY3OTg0MzMzODA4NDM5NWYyM2YyMDgyNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJOZXNzSWNlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U2YjYzODU4YWU2MzM3ZTEwNDYxODA1NjQ2ZWU3NGNiZTEwYmYwYmM2NjllYWQ0YTIzZjA3MTljYmUxMTFiYWQiCiAgICB9CiAgfQp9";
            String signature = "a2IUa5QX3VAnZDknM4InbNizFNSFCX5OBe4gMPGB0kazy5gX0zu0wbnkvOJo8rPAl9ECJviNB6coGTMnQl6QnyK/L5cJCD2EdYYYu+zG4ooXK2Udv9B3x2Z9n4ViWIJIMVKZo05MlKJucjI7LB0Cu68aXYsx1TTDhQurPgqyHpl2gMvIJdyyPTzo8fyfeyNOosI0J7LZyTp2DgnmYgjaiGt6dFzRtuJ/BHcyN3kN1jFBc6Flnwzt7BN5fhaO45j4k1zxsKzPOpeuv5+NXKH7pOUONTGDsduC/ku2wO94m/d1yywrRhV9FxNNTGq6eLZQJ2x/c7qyxETIRf/EuXmCR/flxnsKdbO4ZvYV3jQmZndfe5xqmuoAh1KF3DpcV3qsr0yK1ZbYdlieqgDYpd8nv5YtBmD9csDV3Lf0MPrZy3JZWYamNrdPVQfh9geUeilkqa/C+jRF0X9QL2XfzzKITcBXC8bA4kayW3oTpe7R/Si3rKBeK1q+xevyXUflX+K4ZhfZTSGHVEiVn/pFvc1ytOIBZ9zlYDpwaGUYi6iYW7RTZqI1nrU0/+JTfGhCNkfRroW2RPkjPW//GsyCTfOdcvu8Wz/PtLcqFQMxKsypXjUGAaaLjwxIrawqYuExSlcpybF2hLPdgYPcImiCSXCU3kiuj0MV3bV5dRwNdErGZZU=";
            return new String[]{texture, signature};
        }
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMetadata() { return metadata; }

    public void setMetadata(String metadata) { this.metadata = metadata; }

    public String getTexture() { return texture; }

    public void setTexture(String texture) { this.texture = texture; }

    public String getSignature() { return signature; }

    public void setSignature(String signature) { this.signature = signature; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public EntityPlayer getEntityPlayer() { return entityPlayer; }

    public void setEntityPlayer(EntityPlayer entityPlayer) { this.entityPlayer = entityPlayer; }
}
