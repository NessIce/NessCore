package br.com.ness.core.bukkit.menusystem.menus;

import br.com.ness.core.bukkit.itens.ItemBuilder;
import br.com.ness.core.bukkit.menusystem.Menu;
import br.com.ness.core.bukkit.menusystem.MenuUtils;
import br.com.ness.core.bukkit.message.JsonMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Collections;

public class MinigamesMenu extends Menu {

    public MinigamesMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Minigames";
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

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bContribua com o Fryzzen")){
            player.sendMessage("");
            JsonMessage message = new JsonMessage();
            message.addText(" §bClique ");
            message.addText("§b§lAQUI ", ClickEvent.Action.OPEN_URL, "https://loja.fryzzen.com", HoverEvent.Action.SHOW_TEXT, "§bClique para acessar!");
            message.addText("§bpara acessar nossa loja.");
            message.sendMessage(player);
            player.sendMessage("");
            player.closeInventory();
        }
    }

    @Override
    public void setMenuItens() {
        ItemBuilder help = new ItemBuilder(Material.DIAMOND, "§bContribua com o Fryzzen", Arrays.asList(
                "",
                " §7Você pode ajudar o Fryzzen",
                " §7tornando-se §fVIP §7e adquirindo",
                " §7pacotes de §fCash§7.",
                "",
                " §7Além de ajudar a manter a rede",
                " §7online, você ainda terá acesso",
                " §7a vários §fbenefícios§7!",
                "",
                "§eClique para acessar nossa loja!"));

        ItemBuilder lobby = new ItemBuilder(Material.BOOKSHELF, "§6Lobby Principal", Collections.singletonList("§eClique para entrar!"));

        ItemBuilder skywars = new ItemBuilder(Material.GRASS, "§aSky Wars", Arrays.asList(
                "",
                " §7Você tem medo de altura? Então este",
                " §7jogo não é oara você! No Sky Wars, você",
                " §7deverá eliminar os seus adversários com",
                " §7a ajuda de diversos Kits e Habilidades.",
                "",
                " §7◾ §fSolo",
                " §7◾ §fDuelo",
                " §7◾ §fDupla",
                " §7◾ §fEquipe",
                "",
                "§eClique para jogar!",
                "§70 Jogando."));

        ItemBuilder kitpvp = new ItemBuilder(Material.MUSHROOM_SOUP, "§aKit PvP", Arrays.asList(
                "",
                " §7Treine o seu pvp utilizando os Kits mais",
                " §7diversificados do minecraft! Você pode se",
                " §7recuperar apenas tomando uma sopa quentinha",
                " §7e utilizar poderes unicos de cada Kit.",
                "",
                " §7◾ §fArena 20 Kits",
                " §7◾ §fArena FPS",
                " §7◾ §fDesafio da Lava",
                " §7◾ §f1vs1",
                "",
                "§eClique para jogar!",
                "§70 Jogando."));

        inventory.setItem(10, lobby.getItem());
        inventory.setItem(12, skywars.getItem());
        inventory.setItem(13, kitpvp.getItem());
        inventory.setItem(19, help.getItem());
    }
}
