package me.coaixy.MineCraft;

import me.coaixy.MineCraft.More.Shop;
import me.coaixy.MineCraft.Play.Die;
import me.coaixy.MineCraft.Play.Join;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class DrwG extends JavaPlugin{
    @Override
    public void onLoad() {
        //存放配置文件
        saveDefaultConfig();
        File moneyFile = new File (getDataFolder(),"money.yml");
        try {
            if(!moneyFile.exists())moneyFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File expFile = new File (getDataFolder(),"exp.yml");
        try {
            if (!expFile.exists())expFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File shopFile = new File (getDataFolder(),"shop.yml");
        try {
            if (!shopFile.exists()){
                shopFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File shoplistFile = new File (getDataFolder(),"shoplist.txt");
        try {
            if (!shoplistFile.exists()){
                shoplistFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLogger().info("§a【达尔文游戏】：插件载入成功");
    }
    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new Join(),this);
        getServer().getPluginManager().registerEvents(new Die(),this);
        getServer().getPluginManager().registerEvents(new Shop(),this);
        Main.getLore("§e普通的木剑");
        Main.setLore(new ItemStack(Material.ACACIA_DOOR),"§e普通的木剑");
    }


    @Override
    public void onDisable() {
        getLogger().info("§a【达尔文游戏】：插件卸载成功");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] argv) {
        if (commandSender instanceof Player && command.getName().equalsIgnoreCase("drw")){
            Player player = (Player)commandSender;
            if(argv.length > 0){
                if (argv[0].equalsIgnoreCase("shop")){
                    Main.openShop(player);
                }
            }
        }
        return false;
    }
}
