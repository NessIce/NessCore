package br.com.ness.core.bukkit.profile.hotbar;

import br.com.ness.core.bukkit.menusystem.MenuManager;
import br.com.ness.core.bukkit.menusystem.menus.MinigamesMenu;
import br.com.ness.core.bukkit.menusystem.menus.ProfileMenu;
import br.com.ness.core.bukkit.profile.Profile;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class HotbarActionType {

    private static final Map<Profile, Long> delay = new HashMap<>();
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");;

    private static Map<String, HotbarActionType> actionTypes;
    
    public abstract void execute(Profile profile, String action);
    
    public static void addActionType(String name, HotbarActionType actionType) {
        HotbarActionType.actionTypes.put(name.toLowerCase(), actionType);
    }
    
    public static HotbarActionType fromName(String name) {
        return HotbarActionType.actionTypes.get(name.toLowerCase());
    }
    
    static {
        HotbarActionType.actionTypes = new HashMap<>();

        HotbarActionType.actionTypes.put("comando", new HotbarActionType() {
            @Override
            public void execute(Profile profile, String action) {
                profile.getPlayer().performCommand(action);
            }
        });

        HotbarActionType.actionTypes.put("mensagem", new HotbarActionType() {
            @Override
            public void execute(Profile profile, String action) {
                profile.getPlayer().sendMessage(action);
            }
        });

        HotbarActionType.actionTypes.put("core", new HotbarActionType() {
            @Override
            public void execute(Profile profile, String action) {
                switch (action.toLowerCase()){
                    case "jogos":{
                        new MinigamesMenu(MenuManager.getMenu(profile.getPlayer())).open();
                        break;
                    }
                    case "perfil":{
                        new ProfileMenu(MenuManager.getMenu(profile.getPlayer())).open();
                        break;
                    }
                    case "visibilidade":{
                        long start = delay.getOrDefault(profile, 0L);
                        if (start > System.currentTimeMillis()) {
                            final double time = (start - System.currentTimeMillis()) / 1000.0;
                            if (time > 0.1) {
                                String timeString = DECIMAL_FORMAT.format(time).replace(",", ".");
                                if (timeString.endsWith("0")) {
                                    timeString = timeString.substring(0, timeString.lastIndexOf("."));
                                }
                                profile.sendMessage("§cVocê precisa aguardar mais " + timeString + "s para utilizar novamente.");
                                return;
                            }
                        }
                        delay.put(profile, System.currentTimeMillis() + 3000L);

                        profile.setPlayers(!profile.isPlayers());
                        profile.sendMessage(profile.isPlayers()?"§aVisibilidade de jogadores ativada.":"§cVisibilidade de jogadores desativada.");
                        profile.refreshPlayers();
                        break;
                    }
                }
            }
        });
    }
}
