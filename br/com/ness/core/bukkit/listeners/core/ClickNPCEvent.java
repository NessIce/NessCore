package br.com.ness.core.bukkit.listeners.core;

import br.com.ness.core.bukkit.npc.NPCClickType;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClickNPCEvent extends Event implements Cancellable {

    private Player player;
    private EntityPlayer NPC;
    private NPCClickType clickType;
    private boolean isCancelled;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public ClickNPCEvent(Player player, EntityPlayer NPC, NPCClickType clickType) {
        this.player = player;
        this.NPC = NPC;
        this.clickType = clickType;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityPlayer getNPC() {
        return NPC;
    }

    public NPCClickType getClickType() {
        return clickType;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
