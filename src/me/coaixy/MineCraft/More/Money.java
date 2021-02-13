package me.coaixy.MineCraft.More;
import me.coaixy.MineCraft.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.coaixy.MineCraft.Main.get_shopConfig;

public class Money {

    public int getMoney(Player player){
        int num = 0;
        File moneyFile = new File (Main.get_dataFolder(),"money.yml");
        YamlConfiguration moneyConfig = YamlConfiguration.loadConfiguration(moneyFile);
        num  = moneyConfig.getInt(player.getName());
        return num;
    }
    public void setMoney(String name,int money) throws IOException {
        int num = 0;
        File moneyFile = new File (Main.get_dataFolder(),"money.yml");
        YamlConfiguration moneyConfig = YamlConfiguration.loadConfiguration(moneyFile);
        moneyConfig.set(name,money);
        moneyConfig.save(moneyFile);
    }
    public boolean buy(String displayName, Player player) throws IOException {
        YamlConfiguration shopConfig = get_shopConfig();
        int pay = shopConfig.getInt(displayName+".money");
        Exp exp = new Exp();
        if (getMoney(player)>=pay && exp.getExp(player.getName())>shopConfig.getInt(displayName+".exp-limit")){
            int money = getMoney(player) - pay;
            setMoney(player.getName(),money);
            player.sendMessage("当前余额："+getMoney(player));
            return true;
        }else{
            return false;
        }
    }
}
