package me.coaixy.MineCraft.More;

import me.coaixy.MineCraft.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static me.coaixy.MineCraft.Main.get_shopConfig;
import static me.coaixy.MineCraft.Main.setLore;

public class Shop implements Listener{

    @EventHandler()
    public void onClick(InventoryClickEvent event) throws IOException {
        Inventory inv  = event.getInventory();
        if (inv.getName().equalsIgnoreCase("")){
            Player player = (Player) event.getWhoClicked();
            Money money = new Money();
            String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
            YamlConfiguration shopConfig = get_shopConfig();
            if (money.buy(itemName,player)){
                ItemStack itemStack = new ItemStack(Material.APPLE);
                ItemStack newItemStack = setLore(itemStack,itemName);
                player.getInventory().addItem(newItemStack);//将物品添加进玩家背包
                player.sendMessage("物品已发放给您");
                Main.Scoreboard(player);
            }else{
                player.sendMessage("购买失败 请检查自身情况");
            }
            player.closeInventory();
            Main.Scoreboard(player);
        }
    }
}
