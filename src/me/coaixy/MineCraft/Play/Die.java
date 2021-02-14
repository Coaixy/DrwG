package me.coaixy.MineCraft.Play;

import me.coaixy.MineCraft.Main;
import me.coaixy.MineCraft.More.Exp;
import me.coaixy.MineCraft.More.Money;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

import static me.coaixy.MineCraft.Main.setLore;

public class Die implements Listener {
    @EventHandler()
    public void onDie(EntityDeathEvent event) throws IOException {
        if (event.getEntity() instanceof Player){
            Player player = (Player)event.getEntity();
            Money money = new Money();
            int balance = money.getMoney(player);
            balance = (int) (0.6*balance); //死亡惩罚，扣除40%的金币
            money.setMoney(player.getName(),balance);
            player.getInventory().clear();
            player.updateInventory(); //清空物品栏
            Exp exp = new Exp();
            int exp_balance = exp.getExp(player.getName());
            exp_balance = exp_balance-100;//降低一个等级
            if (exp_balance <= 0)exp_balance=0;
            exp.setExp(player.getName(),exp_balance);
        }
    }
    /*
    §e普通的木剑
    §e普通的皮革大衣
     */
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        ItemStack first = new ItemStack(Material.APPLE);
        ItemStack newFirst = setLore(first,"§e普通的木剑");
        ItemStack second = new ItemStack(Material.APPLE);
        ItemStack newSecond = setLore(second,"§e普通的皮革大衣");
        player.getInventory().addItem(newFirst,newSecond);
        Main.Scoreboard(player);
    }

}
