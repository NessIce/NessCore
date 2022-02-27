package br.com.ness.core.bukkit.listeners.events;

import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.hotbar.HotbarButton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    @EventHandler
    public void registerEvent(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        Profile profile = Profile.getByPlayer(player);

        if (profile.getHotbar() != null) {
            ItemStack item = player.getItemInHand();
            if (evt.getAction().name().contains("CLICK") && item != null && item.hasItemMeta()) {
                HotbarButton button = profile.getHotbar().compareButton(player, item);
                if (button != null) {
                    evt.setCancelled(true);
                    button.getAction().execute(profile);
                }
            }
        }
    }
}
