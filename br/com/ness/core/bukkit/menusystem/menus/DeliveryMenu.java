package br.com.ness.core.bukkit.menusystem.menus;

import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.core.bukkit.menusystem.MenuUtils;
import br.com.ness.core.bukkit.profile.Profile;
import br.com.ness.core.bukkit.profile.delivery.Delivery;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Date;

public class DeliveryMenu extends Menu {

    public DeliveryMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Entregas";
    }

    @Override
    public int getSlots() {
        return 5*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Profile profile = Profile.getByPlayer((Player) e.getWhoClicked());

        if(!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        profile.getPlayer().playSound(profile.getPlayer().getLocation(), Sound.ITEM_PICKUP,1,1);

        for(Delivery delivery : Delivery.getDeliveries().values()){
            if(delivery.getSlot()!=e.getSlot()){
                continue;
            }

            if(!profile.getPlayer().hasPermission(delivery.getPermission())){
                return;
            }

            if(!profile.isDeliveryColletable(delivery)){
                return;
            }

            profile.addDeliveryCowndown(delivery, new Date().getTime() + (3600000L * 24L * 30L));
            profile.sendMessage("§aVocê acabou de coletar "+delivery.getIconClaim().getItemMeta().getDisplayName()+" §a!");

            new DeliveryMenu(MenuManager.getMenu(profile.getPlayer())).open();
            return;
        }

        if(e.getCurrentItem().getType()== Material.ARROW){
            new ProfileMenu(MenuManager.getMenu(profile.getPlayer())).open();
        }
    }

    @Override
    public void setMenuItens() {
        Profile profile = Profile.getByPlayer(menuUtils.getOwner());

        for(Delivery delivery : Delivery.getDeliveries().values()){

            ItemStack item = delivery.getIconClaim();

            if(!profile.getDeliveries().contains(delivery)){
                item = delivery.getIconNoTime(profile.getAllDeliveries().get(delivery));
            }

            if(!profile.getPlayer().hasPermission(delivery.getPermission())){
                item = delivery.getIconNoPerm();
            }

            inventory.setItem(delivery.getSlot(), item);
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW, "§aVoltar", Collections.singletonList("§7Clique para voltar."));
        inventory.setItem(40, back.getItem());
    }
}
