package br.com.ness.core.bukkit.menusystem.menus;

import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.core.bukkit.menusystem.MenuUtils;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collections;

public class SkyWarsStatsMenu extends Menu {

    public SkyWarsStatsMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Estatísticas do Sky Wars";
    }

    @Override
    public int getSlots() {
        return 5*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        if(!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);

        if(e.getCurrentItem().getType()==Material.ARROW){
            new StatsMenu(MenuManager.getMenu(player)).open();
        }
    }

    @Override
    public void setMenuItens() {
        Profile profile = Profile.getByPlayer(menuUtils.getOwner());

        int winsGeral =
                ((Integer)profile.get("skywars", "wins-solo"))+
                ((Integer)profile.get("skywars", "wins-duel"))+
                ((Integer)profile.get("skywars", "wins-double"))+
                ((Integer)profile.get("skywars", "wins-team"));

        int killsGeral =
                ((Integer)profile.get("skywars", "kills-solo"))+
                ((Integer)profile.get("skywars", "kills-duel"))+
                ((Integer)profile.get("skywars", "kills-double"))+
                ((Integer)profile.get("skywars", "kills-team"));

        int deathsGeral =
                ((Integer)profile.get("skywars", "deaths-solo"))+
                ((Integer)profile.get("skywars", "deaths-duel"))+
                ((Integer)profile.get("skywars", "deaths-double"))+
                ((Integer)profile.get("skywars", "deaths-team"));

        int matchesGeral =
                ((Integer)profile.get("skywars", "matches-solo"))+
                ((Integer)profile.get("skywars", "matches-duel"))+
                ((Integer)profile.get("skywars", "matches-double"))+
                ((Integer)profile.get("skywars", "matches-team"));

        ItemBuilder geralStats = new ItemBuilder(Material.GRASS, "§aGeral", Arrays.asList(
                "§fVitórias: §7"+winsGeral,
                "§fAbates: §7"+killsGeral,
                "§fMortes: §7"+deathsGeral,
                "§fPartidas: §7"+matchesGeral
        ));

        ItemBuilder soloStats = new ItemBuilder(Material.STONE_SWORD, "§aModo Solo", Arrays.asList(
                "§fVitórias: §7"+profile.get("skywars", "wins-solo"),
                "§fAbates: §7"+profile.get("skywars", "kills-solo"),
                "§fMortes: §7"+profile.get("skywars", "deaths-solo"),
                "§fPartidas: §7"+profile.get("skywars", "matches-solo")
        ));

        ItemBuilder duelStats = new ItemBuilder(Material.IRON_SWORD, "§aModo Duelo", Arrays.asList(
                "§fVitórias: §7"+profile.get("skywars", "wins-duel"),
                "§fAbates: §7"+profile.get("skywars", "kills-duel"),
                "§fMortes: §7"+profile.get("skywars", "deaths-duel"),
                "§fPartidas: §7"+profile.get("skywars", "matches-duel")
        ));

        ItemBuilder doubleStats = new ItemBuilder(Material.GOLD_SWORD, "§aModo Dupla", Arrays.asList(
                "§fVitórias: §7"+profile.get("skywars", "wins-double"),
                "§fAbates: §7"+profile.get("skywars", "kills-double"),
                "§fMortes: §7"+profile.get("skywars", "deaths-double"),
                "§fPartidas: §7"+profile.get("skywars", "matches-double")
        ));

        ItemBuilder teamStats = new ItemBuilder(Material.DIAMOND_SWORD, "§aModo Equipe", Arrays.asList(
                "§fVitórias: §7"+profile.get("skywars", "wins-team"),
                "§fAbates: §7"+profile.get("skywars", "kills-team"),
                "§fMortes: §7"+profile.get("skywars", "deaths-team"),
                "§fPartidas: §7"+profile.get("skywars", "matches-team")
        ));

        ItemBuilder back = new ItemBuilder(Material.ARROW, "§aVoltar", Collections.singletonList("§7Clique para voltar."));

        inventory.setItem(4, geralStats.getItem());
        inventory.setItem(19, soloStats.getItem());
        inventory.setItem(21, duelStats.getItem());
        inventory.setItem(23, doubleStats.getItem());
        inventory.setItem(25, teamStats.getItem());
        inventory.setItem(40, back.getItem());
    }
}
