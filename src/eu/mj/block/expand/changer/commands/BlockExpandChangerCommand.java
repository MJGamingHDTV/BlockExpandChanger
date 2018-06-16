package eu.mj.block.expand.changer.commands;

import eu.mj.block.expand.changer.BlockExpandChanger;
import eu.mj.block.expand.changer.Scheduler.BECScheduler;
import eu.mj.block.expand.changer.listeners.BlockListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

public class BlockExpandChangerCommand implements CommandExecutor {

    private static FileConfiguration cfg = BlockListeners.cfg;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (p.hasPermission("bec")) {
                    if (cfg.getBoolean("active") ==  false){
                        cfg.set("active", true);
                        p.sendMessage(BlockExpandChanger.prefix + "§aYou activated BEC!");
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    } else {
                        cfg.set("active", false);
                        p.sendMessage(BlockExpandChanger.prefix + "§cYou deactivated BEC!");
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    }
                }
            } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("glass")) {
                        cfg.set("type", "glass");
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("wool")) {
                        cfg.set("type", "wool");
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("clay")) {
                        cfg.set("type", "clay");
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("detect")){
                        Double x = p.getTargetBlock((Set<Material>) null, 3).getLocation().getX();
                        Double y = p.getTargetBlock((Set<Material>) null, 3).getLocation().getY();
                        Double z = p.getTargetBlock((Set<Material>) null, 3).getLocation().getZ();
                        System.out.println(x.intValue() + " " + y.intValue() + " " + z.intValue());
                        World w = p.getWorld();
                        Location loc = new Location(w, x.intValue(), y.intValue(), z.intValue());
                        p.sendMessage(BlockExpandChanger.prefix + "§aYou are standing on the Beacon with the id: " + BECScheduler.idlocs.get(loc));
                        return true;
                    } else {
                        return false;
                    }
                } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("withoutbeacon")) {
                    if (args[1].equalsIgnoreCase("true")) {
                        cfg.set("WorkWithOutBeacon", true);
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    } else {
                        cfg.set("WorkWithOutBeacon", false);
                        try {
                            cfg.save(BlockListeners.f);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    double x = cfg.getDouble(args[1] + ".x");
                    double y = cfg.getDouble(args[1] + ".y");
                    double z = cfg.getDouble(args[1] + ".z");
                    World w = Bukkit.getWorld(cfg.getString(args[1] + ".world"));
                    Location loc = new Location(w, x, y, z);
                    BECScheduler.removeBlocks(loc, BlockExpandChanger.plugin);
                    cfg.set(args[1] + ".x", null);
                    cfg.set(args[1] + ".y", null);
                    cfg.set(args[1] + ".z", null);
                    cfg.set(args[1] + ".world", null);
                    try {
                        cfg.save(BlockListeners.f);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("speed")) {
                    BECScheduler.cancleScheduler();
                    try {
                        long i = Long.valueOf(args[1]);
                        BECScheduler.speed = i;
                        cfg.set("ChangeRateInTicks", i);
                        cfg.save(BlockListeners.f);
                        BECScheduler.Schedule();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                    return true;
                }
            } else {
                return false;
            }
        } else {
            sender.sendMessage(BlockExpandChanger.prefix + "§cYou need to be a player to use this command!");
            return true;
        }
        return false;
    }
}
