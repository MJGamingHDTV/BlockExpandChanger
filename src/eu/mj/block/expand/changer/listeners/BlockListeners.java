package eu.mj.block.expand.changer.listeners;

import eu.mj.block.expand.changer.BlockExpandChanger;
import eu.mj.block.expand.changer.Scheduler.BECScheduler;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.File;
import java.util.ArrayList;

public class BlockListeners implements Listener {

    public static File f = new File("plugins/BlockExpandChanger/", "locations.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public static void setCfgDefaults() {
        cfg.options().copyDefaults(true);
        cfg.addDefault("Nr", 0);
        cfg.addDefault("type", "wool");
        cfg.addDefault("active", false);
        cfg.addDefault("WorkWithOutBeacon", false);
        cfg.addDefault("ChangeRateInTicks", 1);
        cfg.options().copyHeader(true);
        cfg.options().header("Do not change this!");
        try {
            cfg.save(f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadLocations() {
        int i = cfg.getInt("Nr");
        while (i > 0) {
            if (cfg.get(i + ".x") == null) return;
            Double x = cfg.getDouble(i + ".x");
            Double y = cfg.getDouble(i + ".y");
            Double z = cfg.getDouble(i + ".z");
            World w = Bukkit.getWorld(cfg.getString(i + ".world"));
            Location loc = new Location(w, x.intValue(), y.intValue(), z.intValue());
            BECScheduler.idlocs.put(loc, i);
            i--;
        }
    }

    @EventHandler
    public void onBeacon(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType().equals(Material.BEACON)) {
            Location loc = e.getBlockPlaced().getLocation();
            int i = cfg.getInt("Nr");
            i++;
            Double x = loc.getX();
            Double y = loc.getY();
            Double z = loc.getZ();
            cfg.set(i + ".x", x.intValue());
            cfg.set(i + ".y", y.intValue());
            cfg.set(i + ".z", z.intValue());
            cfg.set(i + ".world", loc.getWorld().getName());
            cfg.set("Nr", i);
            BECScheduler.idlocs.put(loc, i);
            try {
                cfg.save(f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Player p = e.getPlayer();
            p.sendMessage(BlockExpandChanger.prefix + "You set a Beacon! Run /bec to activate the Changer!");
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON) && cfg.getBoolean("WorkWithOutBeacon") == false) {
            Location block = e.getBlock().getLocation();
            double x = cfg.getDouble(BECScheduler.idlocs.get(block) + ".x");
            double y = cfg.getDouble(BECScheduler.idlocs.get(block) + ".y");
            double z = cfg.getDouble(BECScheduler.idlocs.get(block) + ".z");
            World w = Bukkit.getWorld(cfg.getString(BECScheduler.idlocs.get(block) + ".world"));
            Location loc = new Location(w, x, y, z);
            cfg.set(BECScheduler.idlocs.get(block) + ".x", null);
            cfg.set(BECScheduler.idlocs.get(block) + ".y", null);
            cfg.set(BECScheduler.idlocs.get(block) + ".z", null);
            cfg.set(BECScheduler.idlocs.get(block) + ".world", null);
            try {
                cfg.save(BlockListeners.f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            BECScheduler.idlocs.remove(block);
            BECScheduler.removeBlocks(loc, BlockExpandChanger.plugin);
        } else if (e.getBlock().getType().equals(Material.STAINED_CLAY) || e.getBlock().getType().equals(Material.STAINED_GLASS) || e.getBlock().getType().equals(Material.WOOL)) {
            for (Location loc : new ArrayList<>(BECScheduler.idlocs.keySet())) {
                Location loc1 = loc.clone();
                Location loc2 = loc.clone();
                Location loc3 = loc.clone();
                Location loc4 = loc.clone();
                Location loc5 = loc.clone();
                Location loc6 = loc.clone();
                Location loc7 = loc.clone();
                Location loc8 = loc.clone();
                if (e.getBlock().getLocation().equals(loc1.add(1, 0, 0))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc2.add(1, 0, -1))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc3.add(0, 0, -1))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc4.add(-1, 0, -1))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc5.add(-1, 0, 0))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc6.add(-1, 0, 1))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc7.add(0, 0, 1))) e.setCancelled(true);
                if (e.getBlock().getLocation().equals(loc8.add(1, 0, 1))) e.setCancelled(true);
            }
        }
    }
}
