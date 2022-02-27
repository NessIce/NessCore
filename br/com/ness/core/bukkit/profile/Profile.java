package br.com.ness.core.bukkit.profile;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.game.GameCore;
import br.com.ness.core.bukkit.groups.Group;
import br.com.ness.core.bukkit.itens.ItemConfig;
import br.com.ness.core.bukkit.profile.delivery.Delivery;
import br.com.ness.core.bukkit.profile.hotbar.Hotbar;
import br.com.ness.core.bukkit.profile.scoreboard.ScoreCore;
import br.com.ness.core.database.DataTable;
import br.com.ness.core.database.Database;
import br.com.ness.core.utils.SerializeObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class Profile {

    private final static Map<Player, Profile> profiles = new HashMap<>();
    private final static Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    public static Profile getByPlayer(Player player){ return profiles.get(player); }
    public static List<Profile> getProfiles(){ return new ArrayList<>(profiles.values()); }
    public static void addProfile(Player player, Profile profile){ profiles.put(player, profile); }
    public static void removeProfile(Player player){ profiles.remove(player); }

    private Player player;
    private ProfileData profileData;
    private ScoreCore scoreCore;
    private GameCore game;
    private Hotbar hotbar;

    public Profile(Player player){
        this.player = player;
        this.profileData = null;
        this.game = null;
        this.hotbar = null;
    }

    //Profile Economy Administration
    public void addCoins(int integer){ profileData.setCoins(profileData.getCoins()+integer); }
    public void removeCoins(int integer){ profileData.setCoins(profileData.getCoins()-integer); }
    public Integer getCoins(){ return profileData.getCoins();}
    public void addCash(int integer){ profileData.setCash(profileData.getCash()+integer); }
    public void removeCash(int integer){ profileData.setCash(profileData.getCash()-integer); }
    public Integer getCash(){ return profileData.getCash();}
    public void addRuby(int integer){ profileData.setRuby(profileData.getRuby()+integer); }
    public void removeRuby(int integer){ profileData.setRuby(profileData.getRuby()-integer); }
    public Integer getRuby(){ return profileData.getRuby();}


    //Profile Game Administration
    public GameCore getGame() { return game; }
    public void setGame(GameCore game) { this.game = game; }
    public boolean isGaming(){ return game!=null; }


    //Profile Hotbar Administration
    public Hotbar getHotbar() { return hotbar; }
    public void setHotbar(Hotbar hotbar) { this.hotbar = hotbar; }


    //Profile Scoreboard Administration
    public void registerScoreCore(){ if(this.scoreCore!=null) this.scoreCore.destroy();this.scoreCore = new ScoreCore(this); }
    public ScoreCore getScoreCore() { return scoreCore; }
    public void setScoreCore(ScoreCore scoreCore) { this.scoreCore = scoreCore; }


    //Profile Group Administration
    public String getDisplayName(){ return getGroup().getPrefix()+player.getName(); }
    public String getColorName(){ return getGroup().getColor()+player.getName(); }
    public Group getGroup(){ return Group.getGroup(profileData.getGroup().toLowerCase()); }
    public void setGroup(Group group){ profileData.setGroup(group.getName()); }


    //Profile Toggles Administration
    public boolean isPlayers() { return profileData.isPlayers();}
    public void setPlayers(boolean players) { profileData.setPlayers(players); }
    public boolean isFly() { return profileData.isFly(); }
    public void setFly(boolean fly) { profileData.setFly(fly); }
    public boolean isTell() { return profileData.isTell(); }
    public void setTell(boolean tell) { profileData.setTell(tell); }
    public boolean isChat() { return profileData.isChat(); }
    public void setChat(boolean chat) { profileData.setChat(chat); }
    public boolean isFriend() { return profileData.isFriend(); }
    public void setFriend(boolean friend) { profileData.setFriend(friend); }
    public boolean isGore() { return profileData.isGore(); }
    public void setGore(boolean gore) { profileData.setGore(gore); }
    public boolean isParty() { return profileData.isParty(); }
    public void setParty(boolean party) { profileData.setParty(party); }
    public boolean isNotify() { return profileData.isNotify(); }
    public void setNotify(boolean notify) { profileData.setNotify(notify); }
    public boolean isLobbyConfirm() { return profileData.isLobbyConfirm(); }
    public void setLobbyConfirm(boolean lobbyConfirm) { profileData.setLobbyConfirm(lobbyConfirm); }


    //Profile Data Container Administration
    public void add(String key, String container, int value){ profileData.getDataContainer().get(key).put(container, ((Integer)get(key,container))+value); }
    public void remove(String key, String container, int value){ profileData.getDataContainer().get(key).put(container, ((Integer)get(key, container))-Math.min(value, ((Integer)get(key, container)))); }
    public Object get(String key, String container){ return profileData.getDataContainer().get(key).get(container); }
    public void set(String key, String container, Object value){ profileData.getDataContainer().get(key).put(container, value); }


    //Profile Delivery Administration
    public boolean hasDeliveries(){ return getDeliveries().size()>0; }
    public boolean isDeliveryColletable(Delivery delivery){ return getDeliveries().contains(delivery); }
    public void addDeliveryCowndown(Delivery delivery, long delay){ this.profileData.getDelivery().put(delivery.getName(), delay); }
    public Map<Delivery, Long> getAllDeliveries(){
        Map<Delivery, Long> deliveryLongMap = new HashMap<>();
        for(String s : profileData.getDelivery().keySet()){
            deliveryLongMap.put(Delivery.getDeliveries().get(s), profileData.getDelivery().get(s));
        }
        return deliveryLongMap;
    }
    public List<Delivery> getDeliveries(){
        List<Delivery> deliveryColletable = new ArrayList<>();
        getAllDeliveries().forEach((d,l)-> {
            if(l<=new Date().getTime()&&player.hasPermission(d.getPermission()))
                deliveryColletable.add(d);
        });
        return deliveryColletable;
    }


    //Profile Information
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public ProfileData getProfileData() { return profileData; }
    public void setProfileData(ProfileData profileData) { this.profileData = profileData; }


    //Profile Permission Administration
    public void reloadPermission(){
        playerPermissions.get(player.getUniqueId()).remove();
        playerPermissions.remove(player.getUniqueId());
        PermissionAttachment attachment = player.addAttachment(CorePlugin.getCore());
        playerPermissions.put(player.getUniqueId(), attachment);
        for(String permissions : getGroup().getPermissions()) {
            attachment.setPermission(permissions, true);
        }
    }


    //Profile Data Administration
    public void load(){
        DataTable dataTable = Database.getAutoBase().getTable("profiles");

        if(!dataTable.contains("player", player.getName())){
            dataTable.insert(new String[]{"`player`", "`data`"},
                    new Object[]{player.getName(), SerializeObject.serialize(new ProfileData(player.getName()))});
        }

        Object data = dataTable.get("player", player.getName(), "data");
        this.profileData = (ProfileData) SerializeObject.deserialize((byte[]) data);

        PermissionAttachment attachment = player.addAttachment(CorePlugin.getCore());
        playerPermissions.put(player.getUniqueId(), attachment);

        for(String permissions : getGroup().getPermissions()) {
            attachment.setPermission(permissions, true);
        }
    }
    public void save(){
        if(getScoreCore()!=null) getScoreCore().destroy();

        playerPermissions.get(player.getUniqueId()).remove();
        playerPermissions.remove(player.getUniqueId());

        Database.getAutoBase().getTable("profiles").set("player", player.getName(), "data",
                SerializeObject.serialize(getProfileData()));
    }


    //Profile Utils Administration
    public void refreshPlayers() {
        Player player = getPlayer();

        if (hotbar != null) {
            this.hotbar.getButtons().forEach(button -> {
                if (button.getAction().getValue().equalsIgnoreCase("visibilidade")) {
                    player.getInventory().setItem(button.getSlot(),  new ItemConfig(button.getIcon())
                            .addHolder("%visible_data%", isPlayers()?"10":"8")
                            .addHolder("%visible_name%", isPlayers()?"§aON":"§cOFF")
                            .addHolder("%visible_lore%", isPlayers()?"desapareçam.":"apareçam.")
                            .addHolder("%player%", player.getName()).getItem());
                }
            });
        }

        if (!isGaming()) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                Profile profile = getByPlayer(players);

                if (!profile.isGaming()) {
                    if ((isPlayers() || profile.getGroup().isAllowVisible())) {
                        player.showPlayer(players);
                    } else {
                        player.hidePlayer(players);
                    }
                }
            }
        }
    }
    public void sendMessage(String message){ player.sendMessage(message); }
}
