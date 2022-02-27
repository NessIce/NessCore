package br.com.ness.core.bukkit.commands.list.essentials;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class GameModeCommand extends BukkitCommand {

    public GameModeCommand(String cmd){
        super(cmd);
        getAliases().add("gm");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("ness.gamemode")){
            player.sendMessage("§cVocê precisa do grupo Gerente ou superior para executar este comando.");
            return true;
        }

        if(args.length >= 1){
            String mode = args[0];
            if(!isInteger(mode)&&(Integer.parseInt(mode)==0||Integer.parseInt(mode)==1||Integer.parseInt(mode)==2||Integer.parseInt(mode)==3)){
                player.sendMessage("§cUtilize /gamemode <0,1,2,3>");
                return true;
            }

            if(args.length == 2){
                if(player.hasPermission("ness.gamemode.admin")){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target==null){
                        player.sendMessage("§cEste jogador não está online.");
                        return true;
                    }
                    player = target;
                }
            }

            player.setGameMode(GameMode.getByValue(Integer.parseInt(mode)));
            player.sendMessage("§aSeu modo de jogo foi alterado para §f"+player.getGameMode().toString()+"§a!");
            return true;
        }
        player.sendMessage("§cUtilize /gamemode <0,1,2,3>");
        return false;
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
