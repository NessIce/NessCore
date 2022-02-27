package br.com.ness.core.bukkit.menusystem;

import org.bukkit.entity.Player;

public class MenuUtils {

    private Player owner;

    public MenuUtils(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
