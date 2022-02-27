package br.com.ness.core.bukkit.menusystem.menus;

import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.core.bukkit.menusystem.MenuUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collections;

public class StatsMenu extends Menu {

    public StatsMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Estatísticas de "+menuUtils.getOwner().getName();
    }

    @Override
    public int getSlots() {
        return 4*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        if(!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bSky Wars")){
            new SkyWarsStatsMenu(MenuManager.getMenu(player)).open();
            return;
        }

        if(e.getCurrentItem().getType()==Material.ARROW){
            new ProfileMenu(MenuManager.getMenu(player)).open();
        }
    }

    @Override
    public void setMenuItens() {
        ItemBuilder skywars = new ItemBuilder(Material.GRASS, "§bSky Wars", Arrays.asList(
                "§7Veja as suas estatísticas",
                "§7no minigame Sky Wars.",
                "",
                "§eClique para ver!"
        ));

        ItemBuilder back = new ItemBuilder(Material.ARROW, "§aVoltar", Collections.singletonList("§7Clique para voltar."));

        inventory.setItem(11, skywars.getItem());
        inventory.setItem(31, back.getItem());
    }
}
