package br.com.ness.core.bukkit.profile.delivery;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.itens.ItemConfig;
import br.com.ness.core.bukkit.utils.SerializerCore;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Delivery {

    public static Map<String, Delivery> deliveries = new HashMap<>();

    public static void loadDeliveries(){
        ConfigFile configFile = new ConfigFile("delivery", CorePlugin.getCore().getDataFolder().getPath(), true, CorePlugin.getCore());

        for(String s : configFile.getSection("deliveries").getKeys(false)){
            int days = configFile.getInteger("deliveries."+s+".days");
            int slot = configFile.getInteger("deliveries."+s+".slot");
            String permission = configFile.getString("deliveries."+s+".permission");
            List<String> rewards = configFile.getStringList("deliveries."+s+".rewards");
            ItemStack iconNoPerm = new ItemConfig(configFile.getString("deliveries."+s+".icon-noperm")).getItem();
            ItemStack iconNoTime = new ItemConfig(configFile.getString("deliveries."+s+".icon-notime")).getItem();
            ItemStack iconClaim = new ItemConfig(configFile.getString("deliveries."+s+".icon-claim")).getItem();

            Delivery delivery = new Delivery(s, days, slot, permission, rewards, iconNoPerm, iconNoTime, iconClaim);
            deliveries.put(delivery.getName(), delivery);
        }
    }

    public static Map<String, Delivery> getDeliveries() { return deliveries; }
    public static void setDeliveries(Map<String, Delivery> deliveries) { Delivery.deliveries = deliveries; }


    private String name;
    private int days;
    private int slot;
    private String permission;
    private List<String> rewards;
    private ItemStack iconNoPerm;
    private ItemStack iconNoTime;
    private ItemStack iconClaim;

    public Delivery(String name, int days, int slot, String permission, List<String> rewards, ItemStack iconNoPerm, ItemStack iconNoTime, ItemStack iconClaim) {
        this.name = name;
        this.days = days;
        this.slot = slot;
        this.permission = permission;
        this.rewards = rewards;
        this.iconNoPerm = iconNoPerm;
        this.iconNoTime = iconNoTime;
        this.iconClaim = iconClaim;
    }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }

    public ItemStack getIconNoPerm() {
        return iconNoPerm;
    }

    public void setIconNoPerm(ItemStack iconNoPerm) {
        this.iconNoPerm = iconNoPerm;
    }

    public ItemStack getIconNoTime(long delay) {
        ItemStack itemStack = iconNoTime.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = SerializerCore.replaceAll(itemMeta.getLore(), "{time}", SerializerCore.getTime(delay-new Date().getTime()));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void setIconNoTime(ItemStack iconNoTime) {
        this.iconNoTime = iconNoTime;
    }

    public ItemStack getIconClaim() {
        return iconClaim;
    }

    public void setIconClaim(ItemStack iconClaim) {
        this.iconClaim = iconClaim;
    }
}
