package eu.mj.block.expand.changer.Scheduler;

import eu.mj.block.expand.changer.BlockExpandChanger;
import eu.mj.block.expand.changer.listeners.BlockListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class BECScheduler {

    private static FileConfiguration cfg = BlockListeners.cfg;
    private static byte id = 0;
    private static int nr = 0;
    private static int mat = 95;
    private static ArrayList<Location> locs = new ArrayList<>();
    public static HashMap<Location, Integer> idlocs = new HashMap<>();
    public static long speed = 1;


    private static void getlocations() {
        int i = cfg.getInt("Nr");
        locs.clear();
        while (i > 0) {
            if (cfg.get(i + ".x") == null) {
                i--;
            } else {
                double x = cfg.getDouble(i + ".x");
                double y = cfg.getDouble(i + ".y");
                double z = cfg.getDouble(i + ".z");
                World w = Bukkit.getWorld(cfg.getString(i + ".world"));
                Location loc = new Location(w, x, y, z);
                locs.add(loc);
                i--;
            }
        }
    }

    private static int taskId;

    public static void Schedule() {

        taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(BlockExpandChanger.plugin, new Runnable() {
            @Override
            public void run() {
                if (cfg.getBoolean("active") == true) {
                    String type = cfg.getString("type");
                    if (type.equalsIgnoreCase("wool")) {
                        mat = 35;
                    } else if (type.equalsIgnoreCase("clay")) {
                        mat = 159;
                    } else if (type.equalsIgnoreCase("glass")) {
                        mat = 95;
                    } else {
                        mat = 159;
                        BlockExpandChanger.sender.sendMessage(BlockExpandChanger.prefix + "§cThere is a mistake in your config! Allowed blocktypes are wool, clay and glass!");
                    }
                    if (id > 15) {
                        id = 0;
                    }
                    getlocations();
                    for (Location loc : locs) {
                        preSetBlock(loc);
                    }
                    nr++;
                    if (nr == 9) {
                        id++;
                    }
                }
            }
        }, 0L, speed);
    }

    public static void cancleScheduler() {
        Bukkit.getServer().getScheduler().cancelTask(taskId);
    }

    private static void preSetBlock(Location loc) {
        if (cfg.getBoolean("WorkWithOutBeacon") == false) {
            if (loc.getBlock().getType().equals(Material.BEACON)) {
                setBlocks(loc);
            }
        } else {
            setBlocks(loc);
        }
    }

    private static void setBlocks(Location loc) {
        if (nr <= 8) {
                switch (nr) {
                    case 1:
                        loc.add(1, 0, 0);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 2:
                        loc.add(1, 0, -1);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 3:
                        loc.add(0, 0, -1);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 4:
                        loc.add(-1, 0, -1);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 5:
                        loc.add(-1, 0, 0);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 6:
                        loc.add(-1, 0, 1);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 7:
                        loc.add(0, 0, 1);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    case 8:
                        loc.add(1, 0, 1);
                        loc.getBlock().setTypeIdAndData(mat, id, true);
                        break;
                    default:
                        int i = 1;
                }
            } else {
                nr = 0;
            }
    }

    private static Location loca;

    public static void removeBlocks(Location location, Plugin pl) {
        loca = location;
        new BukkitRunnable() {
            int i = 1;
            Location loc = loca;
            @Override
            public void run() {
                if (i == 1) {
                    Location loc1 = loc.clone();
                    loc1.add(1, 0, 0);
                    loc1.getBlock().setType(Material.AIR);
                } else if (i == 2) {
                    Location loc2 = loc.clone();
                    loc2.add(1, 0, -1);
                    loc2.getBlock().setType(Material.AIR);
                } else if (i == 3) {
                    Location loc3 = loc.clone();
                    loc3.add(0, 0, -1);
                    loc3.getBlock().setType(Material.AIR);
                } else if (i == 4) {
                    Location loc4 = loc.clone();
                    loc4.add(-1, 0, -1);
                    loc4.getBlock().setType(Material.AIR);
                } else if (i == 5) {
                    Location loc5 = loc.clone();
                    loc5.add(-1, 0, 0);
                    loc5.getBlock().setType(Material.AIR);
                } else if (i == 6) {
                    Location loc6 = loc.clone();
                    loc6.add(-1, 0, 1);
                    loc6.getBlock().setType(Material.AIR);
                } else if (i == 7) {
                    Location loc7 = loc.clone();
                    loc7.add(0, 0, 1);
                    loc7.getBlock().setType(Material.AIR);
                } else if (i == 8) {
                    Location loc8 = loc.clone();
                    loc8.add(1, 0, 1);
                    loc8.getBlock().setType(Material.AIR);
                } else {
                    loc.getBlock().setType(Material.AIR);
                    cancel();
                }
                i++;
            }
        }.runTaskTimer(BlockExpandChanger.plugin, 0L, 5L);
    }
}
