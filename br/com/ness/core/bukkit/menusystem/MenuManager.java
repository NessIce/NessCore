package br.com.ness.core.bukkit.menusystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class MenuManager implements Listener {

    public static Map<Player, MenuUtils> menuPlayerUtilityMap = new HashMap<>();;

    public static MenuUtils getMenu(Player player){
        MenuUtils menuUtils;
        if(menuPlayerUtilityMap.containsKey(player)){
            return menuPlayerUtilityMap.get(player);
        }else{
            menuUtils = new MenuUtils(player);
            menuPlayerUtilityMap.put(player, menuUtils);
            return menuUtils;
        }
    }
}
