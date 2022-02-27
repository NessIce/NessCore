package br.com.ness.core.bukkit.hologram;

import br.com.ness.core.bukkit.CorePlugin;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hologram {

    private String name;
    private Location location;
    private Map<Integer, String> holograms;
    private List<ArmorStand> entitys;

    public Hologram(String name) {
        this.name = name;
        this.entitys = new ArrayList<>();
        this.holograms = new HashMap<>();
    }

    public void spawn(Location location){
        this.location = location;

        for(int line : holograms.keySet()){
            String message = holograms.get(line);
            holoEntity(name, message.replace("&", "§"), location.clone().subtract(0,line*0.3,0));
        }
    }

    public void addLine(String message){
        holograms.put(holograms.size()+1, message);
    }

    public void setLine(int line, String message){
        holograms.put(line, message);
    }

    public void removeLine(int line){
        holograms.remove(line);
    }

    public void despawn(){
        entitys.forEach(Entity::remove);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Map<Integer, String> getHolograms() {
        return holograms;
    }

    public void setHolograms(Map<Integer, String> holograms) {
        this.holograms = holograms;
    }

    public List<ArmorStand> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<ArmorStand> entitys) {
        this.entitys = entitys;
    }

    private ArmorStand holoEntity(String metadata, String message, Location location){
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setCustomName(message);
        armorStand.setCustomNameVisible(true);
        armorStand.setMetadata(metadata, new FixedMetadataValue(CorePlugin.getCore(), armorStand));
        entitys.add(armorStand);
        return armorStand;
    }
}
