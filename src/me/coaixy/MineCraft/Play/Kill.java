package me.coaixy.MineCraft.Play;

import me.coaixy.MineCraft.Main;
import me.coaixy.MineCraft.More.Exp;
import me.coaixy.MineCraft.More.Money;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.IOException;
import java.util.Random;

public class Kill implements Listener {
    @EventHandler
    public void Wild(EntityDeathEvent event) throws IOException {
        if (event.getEntity() instanceof Animals){
            Animals animals = (Animals) event.getEntity();
            Player player = animals.getKiller();
            Random r = new Random(0);
            int m = r.nextInt(30);
            int n = r.nextInt(30);
            Money money = new Money();
            money.setMoney(player.getName(),money.getMoney(player)+m);//奖励金币 [0,30]
            Exp exp = new Exp();
            exp.setExp(player.getName(),exp.getExp(player.getName()+n));
            player.sendMessage("§a你击杀野怪获得了"+m+"金币");
            player.sendMessage("§a你击杀野怪获得了"+n+"经验");
            Main.Scoreboard(player);
        }
    }
    @EventHandler
    public void Player(EntityDeathEvent event) throws IOException {
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            Player Killer = event.getEntity().getKiller();
            Random r = new Random(0);
            Exp exp = new Exp();
            Money money = new Money();
            int n = r.nextInt(30)* exp.getLevel(player.getName()); // [0,30] * 等级
            int m = r.nextInt(30)* exp.getLevel(player.getName());
            money.setMoney(Killer.getName(),money.getMoney(Killer)+m);
            exp.setExp(Killer.getName(),exp.getExp(Killer.getName())+n);
            Killer.sendMessage("§a你击杀玩家获得了"+m+"金币");
            Killer.sendMessage("§a你击杀玩家获得了"+n+"经验");
            Main.Scoreboard(Killer);
        }
    }

}
