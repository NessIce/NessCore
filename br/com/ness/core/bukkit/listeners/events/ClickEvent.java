package br.com.ness.core.bukkit.listeners.events;

import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.hotbar.HotbarButton;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    @EventHandler
    public void registerEvent(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();

        Player player = (Player)e.getWhoClicked();

        if(e.getCurrentItem()==null||e.getCurrentItem().getType()==Material.AIR||!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        ItemStack item = e.getCurrentItem();
        Profile profile = Profile.getByPlayer(player);

        if (profile.getHotbar() != null){
            if (e.getClickedInventory() != null && e.getClickedInventory().equals(player.getInventory()) && item.hasItemMeta()) {
                HotbarButton button = profile.getHotbar().compareButton(player, item);
                if (button != null) {
                    e.setCancelled(true);
                    button.getAction().execute(profile);
                    return;
                }
            }
        }

        if(holder instanceof Menu){
            e.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.handlerMenu(e);
        }
    }
}
