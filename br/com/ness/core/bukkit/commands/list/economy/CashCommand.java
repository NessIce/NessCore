package br.com.ness.core.bukkit.commands.list.economy;

import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class CashCommand extends BukkitCommand {

    public CashCommand(String cmd){
        super(cmd);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!sender.hasPermission("ness.givecash")){
            if(sender instanceof Player){
                sender.sendMessage("§eCash: §f"+ Profile.getByPlayer((Player) sender).getCash());
                return true;
            }
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }

        //cash dar <Jogador> <Quantia>

        if(args.length < 3){
            sender.sendMessage("§cUtilize /cash give <Jogador> <Quantia>");
            return true;
        }

        if(args[0].equalsIgnoreCase("dar") || args[0].equalsIgnoreCase("give")){
            Player player = Bukkit.getPlayer(args[1]);

            if(player == null){
                sender.sendMessage("§cEste jogador não está online!");
                return true;
            }

            Profile profile = Profile.getByPlayer(player);

            try{
                int value = Integer.parseInt(args[2]);
                profile.addCash(value);
                sender.sendMessage("§aVocê deu §f"+value+" §acash para o jogador "+profile.getColorName()+"§a!");
            }catch (NumberFormatException e){
                sender.sendMessage("§cUtilize um valor valido!");
            }
            return true;
        }
        sender.sendMessage("§cUtilize /cash give <Jogador> <Quantia>");
        return false;
    }
}
