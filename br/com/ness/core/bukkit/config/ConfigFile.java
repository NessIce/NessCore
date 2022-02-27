package br.com.ness.core.bukkit.config;

import br.com.ness.core.logger.CoreLogger;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigFile {

    private File file;
    private FileConfiguration config;

    public ConfigFile(String name, String directory, boolean resource, Plugin plugin){
        File dir = new File(directory);
        if(!dir.exists()){
            dir.mkdir();
        }

        file = new File(dir+"/"+name+".yml");
        try{
            if(!file.exists()){
                if(resource) {
                    FileUtils.copyInputStreamToFile(plugin.getResource(name + ".yml"), file);
                }else{
                    file.createNewFile();
                }
            }
        }catch (IOException e){
            CoreLogger.SEVERE.sendMessage("Não foi possivel criar o arquivo "+name+".yml");
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public ConfigurationSection getSection(String section){ return config.getConfigurationSection(section); }

    public String getString(String path){ return config.getString(path).replace("&", "§"); }

    public boolean getBoolean(String path){ return config.getBoolean(path); }

    public Integer getInteger(String path){
        return config.getInt(path);
    }

    public List<String> getStringList(String path){
        return config.getStringList(path);
    }

    public void addStringList(String value, String path){
        List<String> list = config.getStringList(path);
        list.add(value);
        config.set(path, list);
    }

    public void removeStringList(String value, String path){
        List<String> list = config.getStringList(path);
        list.remove(value);
        config.set(path, list);
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
