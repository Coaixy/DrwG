package me.coaixy.MineCraft.Play;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.coaixy.MineCraft.Main;
import java.io.File;

public class Join implements Listener {
    @EventHandler()
    public void onJoin(PlayerJoinEvent event){
        File file = new File("world\\playerdata"+event.getPlayer().getUniqueId()+".dat");
        if (!file.exists()){Main.playerFirst(event.getPlayer().getName());}
        Player player = event.getPlayer();
        File configFile = new File(Main.get_dataFolder(),"config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        String location = "";
        location = config.getInt("Wait.x")+" "+config.getInt("Wait.y")+" "+config.getInt("Wait.z");
        Main.executeCommand("tp "+player.getName()+" "+location);
        Main.Scoreboard(player);
        Location bed = player.getLocation();
        bed.setX(config.getInt("Wait.x"));
        bed.setY(config.getInt("Wait.y"));
        bed.setZ(config.getInt("Wait.z"));
        player.setBedSpawnLocation(bed,true);
    }
}
