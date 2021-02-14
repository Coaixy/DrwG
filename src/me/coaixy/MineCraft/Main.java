package me.coaixy.MineCraft;

import me.coaixy.MineCraft.More.Exp;
import me.coaixy.MineCraft.More.Money;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    /**
     * 输出日志
     *
     * @param Msg
     */
    public static void Log(String Msg) {
        Bukkit.getLogger().info("&e" + Msg);
    }

    /**
     * 服务器执行命令
     *
     * @param command
     */
    public static void executeCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    /**
     * 获取数据文件夹
     *
     * @return
     */
    public static File get_dataFolder() {
        File f = new File("plugins\\DrwG");
        return f;
    }

    /**
     * 初始化玩家数据
     *
     * @param playerName
     */
    public static void playerFirst(String playerName) {
        File moneyFile = new File(get_dataFolder(), "money.yml");
        File expFile = new File(get_dataFolder(), "exp.yml");
        YamlConfiguration money = YamlConfiguration.loadConfiguration(moneyFile);
        YamlConfiguration exp = YamlConfiguration.loadConfiguration(expFile);
        money.set(playerName, 0);
        exp.set(playerName, 0);
    }

    /**
     * 发送计分板数据
     *
     * @param player
     */
    public static void Scoreboard(Player player) {
        Exp exp = new Exp();
        Money money = new Money();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("§e你的个人数据", "§e你的个人数据");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score level = objective.getScore("§s等级");
        level.setScore(exp.getLevel(player.getName()));
        Score Balance = objective.getScore("§s余额");
        Balance.setScore(money.getMoney(player));
        player.setScoreboard(scoreboard);
    }

    /**
     * 获取主配置文件
     *
     * @return Main—YAML
     */
    public static YamlConfiguration get_config() {
        File configFile = new File(get_dataFolder(), "config.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * 获取商品配置文件
     *
     * @return SHOP—YAML
     */
    public static YamlConfiguration get_shopConfig() {
        File configFile = new File(get_dataFolder(), "shop.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * 获取商品数据列表
     *
     * @return 商品列表
     */
    public static String get_shopList() {
        File shoplistFile = new File(get_dataFolder(), "shoplist.txt");
        StringBuilder data = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(shoplistFile));
            String line = "";
            while ((line = in.readLine()) != null) {
                data.append(line).append("###");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 打开商店
     *
     * @param player
     */
    public static void openShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 54, get_config().getString("shop-name"));
        //此处还应添加加入商品的代码
        String shopList = get_shopList();
        String shopItems[] = shopList.split("###");
        for (String itemName : shopItems) {
            ItemStack itemStack = new ItemStack(Material.APPLE);
            ItemStack newItemStack = setLore(itemStack, itemName);
            shop.addItem(newItemStack);//将物品添加进商店
        }
        player.openInventory(shop);
    }

    /**
     * 将物品进行翻译
     *
     * @param itemStack
     * @param itemName
     * @return 配置完毕的物品
     */
    public static ItemStack setLore(ItemStack itemStack, String itemName) {
        try{
            YamlConfiguration shopConfig = get_shopConfig();
            int exp_limit = 0; //经验限制
            String type = null; //装备类型
            String describe = null; //装备描述
            String typeName = null;//翻译后装备类型
            exp_limit = shopConfig.getInt(itemName + ".exp—limit");
            type = shopConfig.getString(itemName + ".type");
            describe = shopConfig.getString(itemName + ".desribe");
            String displayName = "[" + typeName + "]"; //展示名
            displayName += itemName;
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(displayName); //这是展示名
            List loreList = new ArrayList<String>();
            Material material = Material.getMaterial("iron_axe");
            switch (type) { //配置类型
                case "Arm":
                    typeName = "武器";
                    material = Material.getMaterial("iron_axe");
                case "Anti":
                    typeName = "防具";
                    material = Material.getMaterial("iron_helmet");
                case "Accessories":
                    typeName = "饰品";
                    material = Material.getMaterial("IRON_ingot");
                default:
                    typeName = "未知";
                    material = Material.getMaterial("iron_axe");
            }
            loreList.add("物品类型：" + typeName);
            loreList.add("经验限制：" + exp_limit);
            String describes[] = describe.split("\n");
            for (int i = 0; i < describes.length; i++) {
                if (i == 0) {
                    loreList.add("描述：" + describes[0]);
                } else {
                    loreList.add("描述：" + describes[i]);
                }
            }
            itemMeta.setLore(loreList);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }catch (Exception e){
            return itemStack;
        }
    }

    /**
     * 进入等待区
     *
     * @param player
     */
    public static void toWait(Player player) {
        YamlConfiguration config = get_config();
        String location = "";
        location = config.getInt("Wait.x") + " " + config.getInt("Wait.y") + " " + config.getInt("Wait.z");
        Main.executeCommand("tp " + player.getName() + " " + location);
    }

    /**
     *  获取属性的HashMap
     * @param itemName
     * @return 属性值HashMap
     */
    public static HashMap<String,Integer> getLore(String itemName){
        HashMap<String,Integer> lores = new HashMap<>();
        YamlConfiguration shopConfig = get_shopConfig();
        int minDamage = 0;
        int maxDamage = 0;
        int anti = 0;
        String type = null;
        type = shopConfig.getString(itemName+".type");
        if(type.equalsIgnoreCase("Arm")){
            lores.put("type",0);
            minDamage = shopConfig.getInt(itemName+".min-damage");
            maxDamage = shopConfig.getInt(itemName+".max-damage");
            lores.put("minDamage",minDamage);
            lores.put("maxDamage",maxDamage);
        }
        if (type.equalsIgnoreCase("anti")){
            lores.put("type",1);
            anti = shopConfig.getInt(itemName+".anti");
            lores.put("anti",anti);
        }
        if (type.equalsIgnoreCase("accessories")){
            lores.put("type",2);
        }
        return lores;
    }
}
