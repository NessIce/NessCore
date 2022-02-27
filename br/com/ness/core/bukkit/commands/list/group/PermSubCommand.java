package br.com.ness.core.bukkit.commands.list.group;

import br.com.ness.core.bukkit.groups.Group;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


public class PermSubCommand {

    public void execute(CommandSender sender, String[] args){
        if(args.length != 3){
            sender.sendMessage("§cUtilize /group perm (grupo) add/remove (permissão)");
            return;
        }

        String groupName = args[0].toLowerCase();
        String permission = args[2];

        if(!Group.getGroups().containsKey(groupName)){
            sender.sendMessage("§cO grupo citado por você não existe!");
            return;
        }

        Group group = Group.getGroup(groupName);

        if(args[1].equalsIgnoreCase("add")) {
            group.addPermission(permission);
            Bukkit.getOnlinePlayers().forEach(p->Profile.getByPlayer(p).reloadPermission());

            sender.sendMessage("§aVocê adcionou a permissao §f"+permission+" §apara o grupo §f"+group.getDisplayName());
            return;
        }

        if(args[1].equalsIgnoreCase("remove")){
            group.removePermission(permission);
            Bukkit.getOnlinePlayers().forEach(p->Profile.getByPlayer(p).reloadPermission());

            sender.sendMessage("§cVocê removeu a permissao §f"+permission+" §cpara o grupo §f"+group.getDisplayName());
            return;
        }

        sender.sendMessage("§cUtilize /group perm (grupo) add/remove (permissão)");
    }
}
