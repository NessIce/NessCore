package br.com.ness.core.bukkit.profile.hotbar;

import br.com.ness.core.bukkit.itens.ItemConfig;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class Hotbar {

    private final String id;
    private final List<HotbarButton> buttons;
    private static final List<Hotbar> HOTBARS;
    
    public Hotbar(String id) {
        this.id = id;
        this.buttons = new ArrayList<>();
    }
    
    public String getName() {
        return this.id;
    }
    
    public List<HotbarButton> getButtons() {
        return this.buttons;
    }
    
    public void apply(Profile profile) {
        Player player = profile.getPlayer();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        this.buttons.stream().filter(button -> button.getSlot() >= 0 && button.getSlot() <= 8).forEach(button -> {
            ItemStack icon = new ItemConfig(button.getIcon())
                    .addHolder("%visible_data%", profile.isPlayers()?"10":"8")
                    .addHolder("%visible_name%", profile.isPlayers()?"§aON":"§cOFF")
                    .addHolder("%visible_lore%", profile.isPlayers()?"desapareçam.":"apareçam.")
                    .addHolder("%player%", player.getName()).getItem();
            player.getInventory().setItem(button.getSlot(), icon);
        });

        player.updateInventory();
        profile.setHotbar(this);
    }
    
    public HotbarButton compareButton(Player player, ItemStack item) {
        return this.buttons.stream().filter(button -> button.getSlot() >= 0 && button.getSlot() <= 8 && item.equals((Object)player.getInventory().getItem(button.getSlot()))).findFirst().orElse(null);
    }
    
    public static void addHotbar(Hotbar hotbar) {
        Hotbar.HOTBARS.add(hotbar);
    }
    
    public static Hotbar getHotbarById(String id) {
        return Hotbar.HOTBARS.stream().filter(hb -> hb.getName().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
    
    static {
        HOTBARS = new ArrayList<>();
    }
}
