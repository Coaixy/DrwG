package me.coaixy.MineCraft.More;

import me.coaixy.MineCraft.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Exp {
    public int getLevel(String name){
        return (int)getExp(name)/100;
    }
    public int getExp(String name){
        File expFile = new File (Main.get_dataFolder(),"exp.yml");
        YamlConfiguration expConfig = YamlConfiguration.loadConfiguration(expFile);
        return expConfig.getInt(name);
    }
    public void  setExp(String name,int num) throws IOException {
        File expFile = new File (Main.get_dataFolder(),"exp.yml");
        YamlConfiguration expConfig = YamlConfiguration.loadConfiguration(expFile);
        expConfig.set(name,num);
        expConfig.save(expFile);
    }
}
