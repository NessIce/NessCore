package br.com.ness.core.bukkit.menusystem;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;
    protected MenuUtils menuUtils;

    public Menu(MenuUtils menuUtils) {
        this.menuUtils = menuUtils;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handlerMenu(InventoryClickEvent e);

    public abstract void setMenuItens();

    public void open(){
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItens();
        menuUtils.getOwner().openInventory(inventory);
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
