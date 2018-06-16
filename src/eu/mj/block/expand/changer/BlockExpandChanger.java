package eu.mj.block.expand.changer;

import eu.mj.block.expand.changer.Scheduler.BECScheduler;
import eu.mj.block.expand.changer.commands.BlockExpandChangerCommand;
import eu.mj.block.expand.changer.listeners.BlockListeners;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockExpandChanger extends JavaPlugin {

    public static ConsoleCommandSender sender = Bukkit.getConsoleSender();
    public static String prefix = "§8[§9BlockExpandChanger§8] ";
    public static Plugin plugin;

    @Deprecated
    public void onEnable() {
        sender.sendMessage(prefix + "§eis loading...");
        plugin = this;
        BlockListeners.setCfgDefaults();
        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getCommand("blockexpandchanger").setExecutor(new BlockExpandChangerCommand());
        BECScheduler.Schedule();
        BlockListeners.loadLocations();
        sender.sendMessage(prefix + "§awas successfully initialized!");
    }

    public void onDisable() {
        sender.sendMessage(prefix + "§eunload all...");
    }
}
