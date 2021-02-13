package me.coaixy.MineCraft.Play;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static me.coaixy.MineCraft.Main.getLore;
import static me.coaixy.MineCraft.Main.get_shopConfig;

public class Arms {
    @EventHandler
    public void  onDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player beDamaged = (Player) event.getEntity(); //受伤的
            Player damagePlayer = (Player) event.getDamager();  //被攻击的
            ItemStack arm  = damagePlayer.getInventory().getItemInMainHand();
            HashMap<String,Integer> armLore = getLore(arm.getItemMeta().getDisplayName());
            ItemStack accessories = damagePlayer.getInventory().getItemInOffHand();
            HashMap<String,Integer> accessoriesLore = getLore(accessories.getItemMeta().getDisplayName());
            int damage = 0;
            int anti = 0;
            int dodge = 0;
            YamlConfiguration shopConfig = get_shopConfig();
            if (armLore.get("type") == 0){ //武器
                int minDamage = armLore.get("min-damage");
                int maxDamage = armLore.get("max-damage");
                damage =  (int) (Math.random()*(maxDamage-minDamage)+minDamage); //初始数据
            }
            if (armLore.get("type") == 2){ //如果拿饰品打人
                accessories = arm;
                List<String> attribute = shopConfig.getStringList(accessories.getItemMeta().getDisplayName()+".attribute");
                if (attribute.contains("anti")){
                    anti += shopConfig.getInt(accessories.getItemMeta().getDisplayName()+".anti");
                }
                if (attribute.contains("damage")){
                    damage += shopConfig.getInt((accessories.getItemMeta().getDisplayName()+".damage"));
                }
                if (attribute.contains("damage")){
                    dodge = shopConfig.getInt(accessories.getItemMeta().getDisplayName()+".dodge");
                }
            }
            if (accessoriesLore.get("type") == 2){ //副手饰品
                List<String> attribute = shopConfig.getStringList(accessories.getItemMeta().getDisplayName()+".attribute");
                if (attribute.contains("anti")){
                    anti += shopConfig.getInt(accessories.getItemMeta().getDisplayName()+".anti");
                }
                if (attribute.contains("damage")){
                    damage += shopConfig.getInt((accessories.getItemMeta().getDisplayName()+".damage"));
                }
                if (attribute.contains("damage")){
                    dodge = shopConfig.getInt(accessories.getItemMeta().getDisplayName()+".dodge");
                }
            }
            ItemStack[] cloths = beDamaged.getInventory().getArmorContents(); //其顺序为 鞋子、护腿、胸甲、头盔
            for (ItemStack cloth:cloths){
                if (getLore(cloth.getItemMeta().getDisplayName()).get("type") == 1){
                    anti += shopConfig.getInt(accessories.getItemMeta().getDisplayName()+".anti");
                }
            }
            int miss =(int) (Math.random()*(100-1)+1);
            if (miss >= dodge){ //闪避
                damage = damage*(100-anti)/100; //最终结果
                event.setDamage(damage);
            }else{
                event.setDamage(0); //无伤害
            }
        }
    }
}
