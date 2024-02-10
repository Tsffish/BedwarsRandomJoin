package github.tsffish.bedwarsrandomjoin;

import github.tsffish.bedwarsrandomjoin.command.CommandInfo;
import github.tsffish.bedwarsrandomjoin.config.main.MainConfigLoad;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;


public class BedwarsRandomJoin extends JavaPlugin {
    public static Plugin plugin;
    public static final String pluginName = "BedwarsRandomJoin";
    public static final String pluginVersion = "1.0";
    public static String pluginNameConsole = "[BedwarsRandomJoin]";
    public static final String author = "Tsffish";
    public static PluginManager pluginManager = Bukkit.getPluginManager();
    private static final int pluginId = 20967;
    public void onEnable()
    {
        plugin = this;

        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        if (pluginManager.getPlugin("BedwarsRel") != null)
        {

            l(ChatColor.GREEN + " ================================");
            l(" ");
            l(ChatColor.WHITE + pluginName + " " + ChatColor.AQUA + pluginVersion);
            l(" ");
            l(ChatColor.WHITE + "Author: " + ChatColor.YELLOW  + author);
            l(" ");
            l(ChatColor.GREEN + " ================================");

            MainConfigLoad.loadMainConfig(null, true);
            getCommand("bwrj").setExecutor(new CommandInfo());
            getCommand("bwrj open").setExecutor(new CommandInfo());
            getCommand("bwrj join").setExecutor(new CommandInfo());
            l("Command registered");

        } else {
            l(ChatColor.RED + "================================");
            l(" ");
            l(ChatColor.WHITE + pluginName + " " + ChatColor.AQUA + pluginVersion);
            l(" ");
            l(ChatColor.WHITE + "Author: " + ChatColor.YELLOW  + author);
            l(" ");
            l(ChatColor.RED + "================================");

            l(ChatColor.RED + "BedwarsRel not found, unable to enable related support");
                }
    }
        public void onDisable ()
        {
            l("Disabled.");
        }

}