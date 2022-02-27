package br.com.ness.core.logger;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

public enum CoreLogger {

    SEVERE{
        @Override
        public void sendMessage(String message) {
            String messageLogger = "§6[Core] §c"+message;
            if(isBungee()){
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(messageLogger));
            }else{
                Bukkit.getConsoleSender().sendMessage(messageLogger);
            }
        }
    }, WARN {
        @Override
        public void sendMessage(String message) {
            String messageLogger = "§6[Core] §e"+message;
            if(isBungee()){
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(messageLogger));
            }else{
                Bukkit.getConsoleSender().sendMessage(messageLogger);
            }
        }
    }, SUCESS {
        @Override
        public void sendMessage(String message) {
            String messageLogger = "§6[Core] §a"+message;
            if(isBungee()){
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(messageLogger));
            }else{
                Bukkit.getConsoleSender().sendMessage(messageLogger);
            }
        }
    }, DEBUG {
        @Override
        public void sendMessage(String message) {
            String messageLogger = "§6[Core] §e"+message;
            if(isBungee()){
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(messageLogger));
            }else{
                Bukkit.getConsoleSender().sendMessage(messageLogger);
            }
        }
    };

    public boolean isBungee(){
        try {
            Class.forName("org.bukkit.Bukkit");
            return false;
        }catch (ClassNotFoundException ex) {
            try {
                Class.forName("net.md_5.bungee.api.ProxyServer");
                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public abstract void sendMessage(String message);
}
