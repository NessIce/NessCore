package br.com.ness.core.bukkit.commands.list.essentials;

import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class FlyCommand extends BukkitCommand {

    public FlyCommand(String name) {
        super(name);
        getAliases().add("voar");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("ness.fly")){
            player.sendMessage("§cVocê precisa do grupo Alquimista ou superior para executar este comando.");
            return true;
        }

        if(args.length >= 1){
            if(!player.hasPermission("ness.fly.admin")){
                player.sendMessage("§cVocê precisa do grupo Gerente ou superior para executar este comando.");
                return true;
            }
            player = Bukkit.getPlayer(args[0]);

            if(player == null){
                sender.sendMessage("§cEste jogador não está online!");
                return true;
            }
        }

        Profile profile = Profile.getByPlayer(player);
        if(profile.isGaming()){
            sender.sendMessage("§cVocê não pode utilizar este comando em jogo!");
            return true;
        }

        profile.setFly(!profile.isFly());
        player.setAllowFlight(profile.isFly());
        player.sendMessage(profile.isFly()?"§eAgora você pode voar!":"§eAgora você não pode mais voar!");
        return false;
    }
}
