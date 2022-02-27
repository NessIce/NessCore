package br.com.ness.core.bukkit.profile;

import br.com.ness.core.bukkit.profile.delivery.Delivery;
import br.com.ness.core.bukkit.utils.SerializerCore;

import java.io.Serializable;
import java.util.*;

public class ProfileData implements Serializable {

    private String name;

    private int coins, cash, ruby;

    private Map<String, Long> delivery;

    private String group;

    private Map<String, Map<String, Object>> dataContainer;

    private List<String> friendsActive;
    private List<String> friendsPending;
    private List<String> friendsBlocked;

    private boolean players, fly, tell, chat, friend, gore, party, notify, lobbyConfirm;

    public ProfileData(String name) {
        this.name = name;

        this.coins = 0;
        this.ruby = 0;
        this.cash = 0;

        this.delivery = new HashMap<>();
        Delivery.getDeliveries().keySet().forEach(d->this.delivery.put(d,0L));

        this.group = "Jogador";

        this.dataContainer = new HashMap<>();
        this.dataContainer.put("geral", new HashMap<>());

        this.dataContainer.get("geral").put("rank", "Iniciante");
        this.dataContainer.get("geral").put("registered", SerializerCore.getDate(new Date()));
        this.dataContainer.get("geral").put("firstLogin", SerializerCore.getDate(new Date()));
        this.dataContainer.get("geral").put("lastLogin", SerializerCore.getDate(new Date()));
        this.dataContainer.get("geral").put("email", "Nenhum");

        this.dataContainer.put("skywars", new HashMap<>());

        this.dataContainer.get("skywars").put("kitActive-solo",null);
        this.dataContainer.get("skywars").put("kitActive-duel",null);
        this.dataContainer.get("skywars").put("kitActive-double",null);
        this.dataContainer.get("skywars").put("kitActive-team",null);

        this.dataContainer.get("skywars").put("kits-solo",null);
        this.dataContainer.get("skywars").put("kits-duel",null);
        this.dataContainer.get("skywars").put("kits-double",null);
        this.dataContainer.get("skywars").put("kits-team",null);

        this.dataContainer.get("skywars").put("perkActive-solo",null);
        this.dataContainer.get("skywars").put("perkActive-duel",null);
        this.dataContainer.get("skywars").put("perkActive-double",null);
        this.dataContainer.get("skywars").put("perkActive-team",null);

        this.dataContainer.get("skywars").put("perks-solo",null);
        this.dataContainer.get("skywars").put("perks-duel",null);
        this.dataContainer.get("skywars").put("perks-double",null);
        this.dataContainer.get("skywars").put("perks-team",null);

        this.dataContainer.get("skywars").put("kills-solo",0);
        this.dataContainer.get("skywars").put("kills-duel",0);
        this.dataContainer.get("skywars").put("kills-double",0);
        this.dataContainer.get("skywars").put("kills-team",0);

        this.dataContainer.get("skywars").put("wins-solo",0);
        this.dataContainer.get("skywars").put("wins-duel",0);
        this.dataContainer.get("skywars").put("wins-double",0);
        this.dataContainer.get("skywars").put("wins-team",0);

        this.dataContainer.get("skywars").put("deaths-solo",0);
        this.dataContainer.get("skywars").put("deaths-duel",0);
        this.dataContainer.get("skywars").put("deaths-double",0);
        this.dataContainer.get("skywars").put("deaths-team",0);

        this.dataContainer.get("skywars").put("matches-solo",0);
        this.dataContainer.get("skywars").put("matches-duel",0);
        this.dataContainer.get("skywars").put("matches-double",0);
        this.dataContainer.get("skywars").put("matches-team",0);

        this.friendsActive = new ArrayList<>();
        this.friendsPending = new ArrayList<>();
        this.friendsBlocked = new ArrayList<>();

        this.players = true;
        this.fly = false;
        this.tell = true;
        this.chat = true;
        this.friend = true;
        this.gore = true;
        this.party = true;
        this.notify = true;
        this.lobbyConfirm = true;
    }


    //Meta Properties
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    //Meta Economy
    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }
    public int getRuby() { return ruby; }
    public void setRuby(int ruby) { this.ruby = ruby; }
    public int getCash() { return cash; }
    public void setCash(int cash) { this.cash = cash; }


    //Meta Delivery
    public Map<String, Long> getDelivery() { return delivery; }
    public void setDelivery(Map<String, Long> delivery) { this.delivery = delivery; }


    //Meta Group
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }


    //Meta DataContainer
    public Map<String, Map<String, Object>> getDataContainer() { return dataContainer; }
    public void setDataContainer(Map<String, Map<String, Object>> dataContainer) { this.dataContainer = dataContainer; }


    //Meta Friends
    public List<String> getFriendsActive() { return friendsActive; }
    public void setFriendsActive(List<String> friendsActive) { this.friendsActive = friendsActive; }
    public List<String> getFriendsPending() { return friendsPending; }
    public void setFriendsPending(List<String> friendsPending) {this.friendsPending = friendsPending; }
    public List<String> getFriendsBlocked() { return friendsBlocked; }
    public void setFriendsBlocked(List<String> friendsBlocked) { this.friendsBlocked = friendsBlocked; }


    //Meta Toggles
    public boolean isPlayers() { return players;}
    public void setPlayers(boolean players) { this.players = players; }
    public boolean isFly() { return fly; }
    public void setFly(boolean fly) { this.fly = fly; }
    public boolean isTell() { return tell; }
    public void setTell(boolean tell) { this.tell = tell; }
    public boolean isChat() { return chat; }
    public void setChat(boolean chat) { this.chat = chat; }
    public boolean isFriend() { return friend; }
    public void setFriend(boolean friend) { this.friend = friend; }
    public boolean isGore() { return gore; }
    public void setGore(boolean gore) { this.gore = gore; }
    public boolean isParty() { return party; }
    public void setParty(boolean party) { this.party = party; }
    public boolean isNotify() { return notify; }
    public void setNotify(boolean notify) { this.notify = notify; }
    public boolean isLobbyConfirm() { return lobbyConfirm; }
    public void setLobbyConfirm(boolean lobbyConfirm) { this.lobbyConfirm = lobbyConfirm; }
}

