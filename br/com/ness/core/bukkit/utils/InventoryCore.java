package br.com.ness.core.bukkit.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryCore {

    public static List<Integer> slotsAround(Inventory inv){
        int size = inv.getSize();
        List<Integer> integers = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            integers.add(i);
        }
        for(int i = 0;i<size-9;i+=9){
            integers.add(i);
        }
        for(int i = 8;i<size-9;i+=9){
            integers.add(i);
        }
        for(int i = size-9;i<size;i++){
            integers.add(i);
        }

        return integers;
    }

    public static List<Integer> slotsCenter(Inventory inv){
        int size = inv.getSize();
        List<Integer> integers = new ArrayList<>();

        for(int i = 0;i<size;i++){
            integers.add(i);
        }
        slotsAround(inv).forEach(integers::remove);

        return integers;
    }

    public static void fillInvetory(Inventory inventory, ItemStack item){
        for(int slot = 0;slot<inventory.getSize();slot++){
            if(inventory.getItem(slot)==null){
                inventory.setItem(slot, item);
            }
        }
    }
}
