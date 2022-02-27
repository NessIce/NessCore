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

public class PreferenceMenu extends Menu {

    public PreferenceMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "§8Preferências";
    }

    @Override
    public int getSlots() {
        return 6*9;
    }

    @Override
    public void handlerMenu(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        if(!e.getCurrentItem().hasItemMeta()||!e.getCurrentItem().getItemMeta().hasDisplayName()){
            return;
        }

        player.playSound(player.getLocation(), Sound.ITEM_PICKUP,1,1);

        Profile profile = Profile.getByPlayer(player);

        switch (e.getCurrentItem().getItemMeta().getDisplayName().replace("§a","").replace("§c","")){
            case "Visibilidade":{
                profile.setPlayers(!profile.isPlayers());
                profile.refreshPlayers();
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }
            case "Voar no lobby":{
                if(player.hasPermission("ness.fly")){
                    profile.setFly(!profile.isFly());
                    player.setAllowFlight(profile.isFly());
                    new PreferenceMenu(MenuManager.getMenu(player)).open();
                }
                break;
            }
            case "Mensagens privadas":{
                profile.setTell(!profile.isTell());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }
            case "Chat":{
                profile.setChat(!profile.isChat());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }

            case "Solicitações de amizade":{
                profile.setFriend(!profile.isFriend());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }

            case "Sangue":{
                profile.setGore(!profile.isGore());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }

            case "Pedidos de party":{
                profile.setParty(!profile.isParty());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }

            case "Notificações de chat":{
                profile.setNotify(!profile.isNotify());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }

            case "Confirmação de /lobby":{
                profile.setLobbyConfirm(!profile.isLobbyConfirm());
                new PreferenceMenu(MenuManager.getMenu(player)).open();
                break;
            }
        }

        if(e.getCurrentItem().getType()==Material.ARROW){
            new ProfileMenu(MenuManager.getMenu(player)).open();
        }
    }

    @Override
    public void setMenuItens() {
        Profile profile = Profile.getByPlayer(menuUtils.getOwner());

        String playersName = profile.isPlayers() ? "§aVisibilidade" : "§cVisibilidade";
        String playersState = profile.isPlayers() ? "§aAtivado" : "§cDesativado";
        int playersData = profile.isPlayers() ? 5 : 14;
        ItemBuilder players = new ItemBuilder(Material.WATCH, playersName, Collections.singletonList("§7Ver outros jogadores no lobby."));
        ItemBuilder playersToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, playersName, playersData, Collections.singletonList("§fEstado: " + playersState));

        String flyName = profile.isFly() ? "§aVoar no lobby" : "§cVoar no lobby";
        String flyState = profile.isFly() ? "§aAtivado" : "§cDesativado";
        int flyData = profile.isFly() ? 5 : 14;
        ItemBuilder fly = new ItemBuilder(Material.FEATHER, flyName, Collections.singletonList("§7Habilitar automaticamente o voo no lobby."));
        ItemBuilder flyToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, flyName, flyData, Collections.singletonList("§fEstado: " + flyState));

        String tellName = profile.isTell() ? "§aMensagens privadas" : "§cMensagens privadas";
        String tellState = profile.isTell() ? "§aAtivado" : "§cDesativado";
        int tellData = profile.isTell() ? 5 : 14;
        ItemBuilder tell = new ItemBuilder(Material.EMPTY_MAP, tellName, Collections.singletonList("§7Receber mensagens privadas."));
        ItemBuilder tellToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, tellName, tellData, Collections.singletonList("§fEstado: " + tellState));

        String chatName = profile.isChat() ? "§aChat" : "§cChat";
        String chatState = profile.isChat() ? "§aAtivado" : "§cDesativado";
        int chatData = profile.isChat() ? 5 : 14;
        ItemBuilder chat = new ItemBuilder(Material.PAPER, chatName, Arrays.asList("§7Receber as mensagens enviadas", "§7no chat por outros jogadores."));
        ItemBuilder chatToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, chatName, chatData, Collections.singletonList("§fEstado: " + chatState));

        String friendName = profile.isFriend() ? "§aSolicitações de amizade" : "§cSolicitações de amizade";
        String friendState = profile.isFriend() ? "§aAtivado" : "§cDesativado";
        int friendData = profile.isFriend() ? 5 : 14;
        ItemBuilder friend = new ItemBuilder(Material.SKULL_ITEM, friendName, 3, Collections.singletonList("§7Receber solicitações de /amigo."));
        ItemBuilder friendToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, friendName, friendData, Collections.singletonList("§fEstado: " + friendState));

        String goreName = profile.isGore() ? "§aSangue" : "§cSangue";
        String goreState = profile.isGore() ? "§aAtivado" : "§cDesativado";
        int goreData = profile.isGore() ? 5 : 14;
        ItemBuilder gore = new ItemBuilder(Material.REDSTONE, goreName, Arrays.asList("§7Ver particulas de sangue", "§7ao atacar um jogador."));
        ItemBuilder goreToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, goreName, goreData, Collections.singletonList("§fEstado: " + goreState));

        String partyName = profile.isParty() ? "§aPedidos de party" : "§cPedidos de party";
        String partyState = profile.isParty() ? "§aAtivado" : "§cDesativado";
        int partyData = profile.isParty() ? 5 : 14;
        ItemBuilder party = new ItemBuilder(Material.RED_ROSE, partyName, Collections.singletonList("§7Receber pedidos de /party."));
        ItemBuilder partyToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, partyName, partyData, Collections.singletonList("§fEstado: " + partyState));

        String notifyName = profile.isNotify() ? "§aNotificações de chat" : "§cNotificações de chat";
        String notifyState = profile.isNotify() ? "§aAtivado" : "§cDesativado";
        int notifyData = profile.isNotify() ? 5 : 14;
        ItemBuilder notify = new ItemBuilder(Material.MAP, notifyName, Arrays.asList("§7Receber notificações sonoras sempre", "§7que um jogador mecionar você no chat", "§7dos lobbies"));
        ItemBuilder notifyToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, notifyName, notifyData, Collections.singletonList("§fEstado: " + notifyState));

        String lobbyName = profile.isLobbyConfirm() ? "§aConfirmação de /lobby" : "§cConfirmação de /lobby";
        String lobbyState = profile.isLobbyConfirm() ? "§aAtivado" : "§cDesativado";
        int lobbyData = profile.isLobbyConfirm() ? 5 : 14;
        ItemBuilder lobby = new ItemBuilder(Material.BEACON, lobbyName, Collections.singletonList("§7Confirmar o comando /lobby."));
        ItemBuilder lobbyToggle = new ItemBuilder(Material.STAINED_GLASS_PANE, lobbyName, lobbyData, Collections.singletonList("§fEstado: " + lobbyState));

        ItemBuilder back = new ItemBuilder(Material.ARROW, "§aVoltar", Collections.singletonList("§7Clique para voltar."));

        inventory.setItem(1, players.getItem());
        inventory.setItem(2, fly.getItem());
        inventory.setItem(3, tell.getItem());
        inventory.setItem(4, chat.getItem());
        inventory.setItem(5, friend.getItem());
        inventory.setItem(6, gore.getItem());
        inventory.setItem(7, party.getItem());
        inventory.setItem(28, notify.getItem());
        inventory.setItem(29, lobby.getItem());

        inventory.setItem(10, playersToggle.getItem());
        inventory.setItem(11, flyToggle.getItem());
        inventory.setItem(12, tellToggle.getItem());
        inventory.setItem(13, chatToggle.getItem());
        inventory.setItem(14, friendToggle.getItem());
        inventory.setItem(15, goreToggle.getItem());
        inventory.setItem(16, partyToggle.getItem());
        inventory.setItem(37, notifyToggle.getItem());
        inventory.setItem(38, lobbyToggle.getItem());

        inventory.setItem(49, back.getItem());
    }
}
