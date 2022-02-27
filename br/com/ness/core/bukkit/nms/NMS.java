package br.com.ness.core.bukkit.nms;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class NMS {

    public static void sendTablist(Player player){
        StringJoiner header = new StringJoiner("\n");
        header.add("");
        header.add("§r                                     §6§lFRYZZEN                                     §r");
        header.add("§fjogar.fryzzen.com");
        header.add("");

        StringJoiner footer = new StringJoiner("\n");
        footer.add("");
        footer.add("§eTwitter: §fwww.fryzzen.com/twitter");
        footer.add("§eDiscord: §fwww.fryzzen.com/discord");
        footer.add("");
        footer.add("§eVocê pode adquirir VIP ou CASH em nossa loja: §bloja.fryzzen.com");
        footer.add("");

        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header.toString() + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer.toString() + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, tabHeader);
            headerField.setAccessible(false);
            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, tabFooter);
            footerField.setAccessible(false);
        } catch (Exception e) { e.printStackTrace(); }
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
