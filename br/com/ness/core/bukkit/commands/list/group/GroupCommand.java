package br.com.ness.core.bukkit.commands.list.group;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.config.ConfigFile;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.List;

public class GroupCommand extends BukkitCommand {

    public GroupCommand(String cmd){
        super(cmd);
        getAliases().add("grupo");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if(!sender.hasPermission("ness.groups")){
            sender.sendMessage("§cVocê precisa do grupo Gerente ou superior para executar este comando.");
            return true;
        }

        if(args.length != 0) {
            String argument = args[0];
            List<String> newArgs = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                if (i == 0) { continue; }
                newArgs.add(args[i]);
            }
            if(argument.equalsIgnoreCase("perm")){
                new PermSubCommand().execute(sender, newArgs.toArray(new String[0]));
                return true;
            }
            if(argument.equalsIgnoreCase("user")){
                new UserSubCommand().execute(sender, newArgs.toArray(new String[0]));
                return true;
            }
            if(argument.equalsIgnoreCase("reload")){
                new ConfigFile("groups", CorePlugin.getCore().getDataFolder().getPath(), true, CorePlugin.getCore()).reload();
                Bukkit.getOnlinePlayers().forEach(p-> Profile.getByPlayer(p).reloadPermission());
                sender.sendMessage("§aAs permissões foram recarregadas na config!");
                return true;
            }
        }

        sender.sendMessage("");
        sender.sendMessage(" §c§lADMINISTRAR GRUPOS");
        sender.sendMessage("");
        sender.sendMessage(" §c/grupo perm <Grupo> add/remove <Permissão>");
        sender.sendMessage(" §c/grupo user <Jogador> <Grupo>");
        sender.sendMessage(" §c/grupo reload");
        sender.sendMessage("");
        return false;
    }
}
