package br.com.ness.core.bukkit.message;

import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

public class JsonMessage {

    private BaseComponent message;

    public JsonMessage() {
        this.message = new TextComponent("");
    }

    public BaseComponent getMessage() {
        return message;
    }

    public void setMessage(BaseComponent message) {
        this.message = message;
    }

    public void addText(String message) {
        this.message.addExtra(new TextComponent(message));
    }

    public void addText(String message, ClickEvent.Action action, String value) {
        TextComponent textComponent = new TextComponent(message);
        textComponent.setClickEvent(new ClickEvent(action, value));
        this.message.addExtra(textComponent);
    }

    public void addText(String message, HoverEvent.Action action, String value) {
        TextComponent textComponent = new TextComponent(message);
        textComponent.setHoverEvent(new HoverEvent(action, new ComponentBuilder(value).create()));
        this.message.addExtra(textComponent);
    }

    public void addText(String message, ClickEvent.Action click, String valueclick, HoverEvent.Action hover, String valuehover) {
        TextComponent textComponent = new TextComponent(message);
        textComponent.setClickEvent(new ClickEvent(click, valueclick));
        textComponent.setHoverEvent(new HoverEvent(hover, new ComponentBuilder(valuehover).create()));
        this.message.addExtra(textComponent);
    }

    public void sendMessage(Player player) {
        player.spigot().sendMessage(this.message);
    }
}

