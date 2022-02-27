package br.com.ness.core.bukkit.commands.list.group;

import br.com.ness.core.bukkit.groups.Group;
import br.com.ness.core.bukkit.listeners.core.GroupChangeEvent;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserSubCommand {

    public void execute(CommandSender sender, String[] args){
        if(args.length < 1){
            sender.sendMessage("§cUtilize /group user set/info <Jogador> <Grupo>");
            return;
        }

        String subCommand = args[0].toLowerCase();
        Player player = Bukkit.getPlayer(args[1]);

        if(player == null){
            sender.sendMessage("§cEste jogador não está online!");
            return;
        }

        Profile profile = Profile.getByPlayer(player);

        Group lastGroup = profile.getGroup();

        if(subCommand.equals("info")){
            sender.sendMessage("");
            sender.sendMessage(" §e§lINFORMAÇÕES DO JOGADOR");
            sender.sendMessage(" "+profile.getDisplayName());
            sender.sendMessage("");
            sender.sendMessage(" §fNome: "+profile.getColorName());
            sender.sendMessage(" §fGrupo: "+profile.getGroup().getDisplayName());
            sender.sendMessage("");
            return;
        }

        if(subCommand.equals("set") && args.length>2){
            String groupName = args[2].toLowerCase();

            if(!Group.getGroups().containsKey(groupName)){
                sender.sendMessage("§cO grupo citado por você não existe!");
                return;
            }

            Group actualGroup = Group.getGroup(groupName);

            if(lastGroup.getName().equals(actualGroup.getName())){
                sender.sendMessage("§cEste jogador já pertence ao grupo "+actualGroup.getName());
                return;
            }

            profile.setGroup(actualGroup);

            GroupChangeEvent groupEvent = new GroupChangeEvent(player, lastGroup, actualGroup);
            Bukkit.getPluginManager().callEvent(groupEvent);

            profile.reloadPermission();

            sender.sendMessage("§aVocê definiu o grupo "+actualGroup.getDisplayName()+" §apara o jogador §f"+profile.getColorName()+"§a!");
            return;
        }

        sender.sendMessage("§cUtilize /group user set/info <Jogador> <Grupo>");
    }
}
