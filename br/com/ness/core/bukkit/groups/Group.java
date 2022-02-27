package br.com.ness.core.bukkit.groups;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.config.ConfigFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {

    private static Map<String, Group> groups = new HashMap<>();

    public static void loadGroups(){
        ConfigFile configFile = new ConfigFile("groups", CorePlugin.getCore().getDataFolder().getPath(), true, CorePlugin.getCore());

        for(String s : configFile.getSection("groups").getKeys(false)){
            String displayName = configFile.getString("groups."+s+".displayname").replace("&", "§");
            String prefix = configFile.getString("groups."+s+".prefix").replace("&", "§");
            String color = configFile.getString("groups."+s+".color").replace("&", "§");
            String team = configFile.getString("groups."+s+".team");
            boolean allowVisible = configFile.getBoolean("groups."+s+".allowVisible");

            List<String> permissions = configFile.getStringList("permissions."+s);

            groups.put(s.toLowerCase(), new Group(s,displayName,prefix,color,team,permissions, allowVisible));
        }
    }

    public static Group getGroup(String name){ return groups.get(name); }
    public static Map<String, Group> getGroups() { return groups; }
    public static void setGroups(Map<String, Group> groups) { Group.groups = groups; }

    private String name;
    private String displayName;
    private String prefix;
    private String color;
    private String team;
    private List<String> permissions;
    private boolean allowVisible;

    public Group(String name, String displayName, String prefix, String color, String team, List<String> permissions, boolean allowVisible) {
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.color = color;
        this.team = team;
        this.permissions = permissions;
        this.allowVisible = allowVisible;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
    public boolean isAllowVisible() { return allowVisible; }
    public void setAllowVisible(boolean allowVisible) { this.allowVisible = allowVisible; }

    public void addPermission(String permission){
        ConfigFile configFile = new ConfigFile("groups", CorePlugin.getCore().getDataFolder().getPath(), true, CorePlugin.getCore());

        List<String> perms = configFile.getStringList("permissions."+getName());
        perms.add(permission);
        configFile.getConfig().set("permissions."+getName(), perms);
        configFile.save();

        permissions.add(permission);
    }

    public void removePermission(String permission){
        ConfigFile configFile = new ConfigFile("groups", CorePlugin.getCore().getDataFolder().getPath(), true, CorePlugin.getCore());

        List<String> perms = configFile.getStringList("permissions."+getName());
        perms.remove(permission);
        configFile.getConfig().set("permissions."+getName(), perms);
        configFile.save();

        permissions.remove(permission);
    }

    public List<String> getPermissions() {
        return this.permissions;
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
