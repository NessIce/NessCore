package br.com.ness.core.bungee;

import br.com.ness.core.bungee.listeners.ChangeEvent;
import br.com.ness.core.logger.CoreLogger;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungee extends Plugin {

    @Override
    public void onEnable() {
        core = this;

        getProxy().registerChannel("fryzzen:channel");
        getProxy().getPluginManager().registerListener(this, new ChangeEvent());

        CoreLogger.SUCESS.sendMessage("ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        CoreLogger.SEVERE.sendMessage("desativado com sucesso!");
    }

    private static CoreBungee core;
    public static CoreBungee getCore() { return core;}
}
