package br.com.ness.core.bukkit.commands.list;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.itens.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class FriendCommand extends BukkitCommand{


    public FriendCommand(String cmd){
        super(cmd);
    }

    private final ArrayList<ArmorStand> armorStands = new ArrayList<>();

    @Override// /amigo aceitar/recusar/bloquear <>
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }

        Player player = (Player)sender;

        ArmorStand as = player.getWorld().spawn(player.getLocation().clone().subtract(0, 1.72, 0), ArmorStand.class);
        as.setVisible(false);
        as.setCanPickupItems(false);
        as.setArms(false);
        as.setBasePlate(false);
        as.setGravity(false);


        //CABEÇA
        ItemBuilder skull = new ItemBuilder("http://textures.minecraft.net/texture/176b4e390f2ecdb8a78dc611789ca0af1e7e09229319c3a7aa8209b63b9", "Giraffe", new ArrayList<>());
        as.setHelmet(skull.getItem());
        armorStands.add(as);


        //PESCOÇO
        ItemBuilder snek = new ItemBuilder("http://textures.minecraft.net/texture/87fc0ef9cd111afae56b89925b08982a0abc0098979435d8cac56940ebf71dc3", "Giraffe", new ArrayList<>());

        as = player.getWorld().spawn(player.getLocation().clone().subtract(0, 0.4, 0), ArmorStand.class);
        as.setSmall(true);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(0, 0.8, 0), ArmorStand.class);
        as.setSmall(true);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(0, 1.2, 0), ArmorStand.class);
        as.setSmall(true);
        as.setHelmet(snek.getItem());
        armorStands.add(as);


        //CORPO
        ItemBuilder body = new ItemBuilder("http://textures.minecraft.net/texture/87fc0ef9cd111afae56b89925b08982a0abc0098979435d8cac56940ebf71dc3", "Giraffe", new ArrayList<>());

        as = player.getWorld().spawn(player.getLocation().clone().subtract(0.15, 1.6, 0), ArmorStand.class);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(0.15, 1.6, -0.5), ArmorStand.class);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(0.15, 1.6, -1), ArmorStand.class);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(-0.15, 1.6, 0), ArmorStand.class);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(-0.15, 1.6, -0.5), ArmorStand.class);
        as.setHelmet(snek.getItem());
        armorStands.add(as);

        as = player.getWorld().spawn(player.getLocation().clone().subtract(-0.15, 1.6, -1), ArmorStand.class);
        as.setHelmet(snek.getItem());
        armorStands.add(as);


        new BukkitRunnable() {
            public void run() {
                for (ArmorStand stand : armorStands) {
                    float yaw = (float) Math
                            .toDegrees(Math.atan2(player.getLocation().getZ() - stand.getLocation().getZ(),
                                    player.getLocation().getX() - stand.getLocation().getX()))
                            - 90;
                    float pitch = (float) Math
                            .toDegrees(Math.atan2(player.getLocation().getZ() - stand.getLocation().getZ(),
                                    player.getLocation().getX() - stand.getLocation().getX()))
                            - 90;


                    if (player.getLocation().distanceSquared(stand.getLocation()) >= 8) {
                        Location loc = stand.getLocation().add(player.getLocation().toVector()
                                .subtract(stand.getLocation().toVector()).setY(0).normalize().multiply(0.3));
                        loc.setYaw(yaw);
                        loc.setYaw(pitch);

                        stand.teleport(loc);

                    }
                }
            }

        }.runTaskTimer(CorePlugin.getCore(), 0, 1);// To run the Runnable
        return false;
    }
}
