package br.com.ness.core.bukkit.listeners.core;

import br.com.ness.core.bukkit.groups.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GroupChangeEvent extends Event implements Cancellable {

    private Player player;
    private Group last;
    private Group actual;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public GroupChangeEvent(Player player, Group last, Group actual){
        this.player = player;
        this.last = last;
        this.actual = actual;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Group getLast() {
        return last;
    }

    public void setLast(Group last) {
        this.last = last;
    }

    public Group getActual() {
        return actual;
    }

    public void setActual(Group actual) {
        this.actual = actual;
    }
}
