package me.Stellrow.InstantEat;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class InstantEat extends JavaPlugin {
    public HashMap<Material,Integer> foods = new HashMap<Material,Integer>();

    public void onEnable(){
    loadConfig();
    loadFoods();
    getServer().getPluginManager().registerEvents(new InstantEatEvents(this),this);
    }
    private void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    public void loadFoods(){
        for(String s : getConfig().getConfigurationSection("Times").getKeys(false)){
            foods.put(Material.valueOf(s),getConfig().getInt("Times."+s));
        }
    }
}
