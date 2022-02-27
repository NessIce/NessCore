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

public class ProfileMenu extends Menu {

    public ProfileMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Perfil";
    }

    @Override
    public int getSlots() {
        return 3*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        if(!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aPreferências")){
            new PreferenceMenu(MenuManager.getMenu(player)).open();
            return;
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aEstatísticas")){
            new StatsMenu(MenuManager.getMenu(player)).open();
            return;
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aEntregas")){
            new DeliveryMenu(MenuManager.getMenu(player)).open();
        }
    }

    @Override
    public void setMenuItens() {
        Profile profile = Profile.getByPlayer(menuUtils.getOwner());

        ItemBuilder preference = new ItemBuilder(Material.REDSTONE_COMPARATOR, "§aPreferências", Collections.singletonList("§7Controle suas preferências pessoais."));
        ItemBuilder stats = new ItemBuilder(Material.PAPER, "§aEstatísticas", Arrays.asList("§7Veja todas as suas estatísticas", "§7de todos os nossos minigames."));

        ItemBuilder info = new ItemBuilder(profile.getPlayer(), "§aInformações Pessoais", Arrays.asList(
                "§fGrupo: §7"+ profile.getGroup().getDisplayName(),
                "§fCash: §a"+ profile.getCash(),
                "§fRuby: §a"+ profile.getRuby(),
                "",
                "§fCadastrado em: §7"+profile.get("geral", "registered"),
                "§fPrimeiro login: §7"+profile.get("geral", "firstLogin"),
                "§fÚltimo login: §7"+profile.get("geral", "lastLogin"),
                "",
                "§fEmail: §7"+profile.get("geral", "email")));

        ItemBuilder delivery = new ItemBuilder(profile.hasDeliveries()?Material.STORAGE_MINECART:Material.MINECART, "§aEntregas", Collections.singletonList(profile.hasDeliveries()?
                "§fVocê possui §a"+profile.getDeliveries().size()+" §fentregas para coletar!":
                "§7Você não possui entrega para coletar."));

        ItemBuilder upgrade = new ItemBuilder(Material.EXP_BOTTLE, "§aEvolua seu VIP", Arrays.asList( "§cAinda tou fazendo...", "§eClique para upar."));

        inventory.setItem(10, preference.getItem());
        inventory.setItem(11, stats.getItem());
        inventory.setItem(13, info.getItem());
        inventory.setItem(15, delivery.getItem());
        inventory.setItem(16, upgrade.getItem());
    }
}
