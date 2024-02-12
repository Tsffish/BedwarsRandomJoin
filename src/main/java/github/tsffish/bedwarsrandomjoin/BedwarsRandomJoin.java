package github.tsffish.bedwarsrandomjoin;

import github.tsffish.bedwarsrandomjoin.command.CommandInfo;
import github.tsffish.bedwarsrandomjoin.config.main.MainConfigLoad;
import github.tsffish.bedwarsrandomjoin.util.update.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;


public class BedwarsRandomJoin extends JavaPlugin {
    public static final String pluginName = "BedwarsRandomJoin";
    public static final String pluginVersion = "1.0.2";
    public static final String pluginNameConsole = "[BedwarsRandomJoin]";
    public static final String author = "Tsffish";
    public static PluginManager pluginManager = Bukkit.getPluginManager();
    private static final int pluginId = 20967;
    public static String language;
    public static boolean isLastestVersion;
    public void onEnable()
    {

        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        Locale currentLocale = Locale.getDefault();
        language = currentLocale.getLanguage();

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
    public static void checkUpdate(int resId)
    {
        new UpdateChecker(JavaPlugin.getProvidingPlugin(BedwarsRandomJoin.class), resId).getVersion(version ->
        {
            if (Bukkit.getPluginManager().getPlugin("BedwarsRandomJoin").getDescription().getVersion().equals(version))
            {
                isLastestVersion = true;
            } else
            {
                isLastestVersion = false;
                if (language.equalsIgnoreCase("zh")) {
                    l(ChatColor.WHITE + " ================================");
                    l(ChatColor.WHITE + " ");
                    l(ChatColor.WHITE + pluginName + ChatColor.GREEN + " 发现新版本！");
                    l(ChatColor.WHITE + " ");
                    l(ChatColor.WHITE + "你可以在此处下载: https://www.spigotmc.org/resources/bedwarsrandomjoin.115020/");
                    l(ChatColor.WHITE + "如果不想检查更新，请将config.yml里的update_checker设置为false");
                    l(ChatColor.WHITE + " ");
                    l(ChatColor.WHITE + " ================================");
                } else
                {
                    l(ChatColor.WHITE + " ================================");
                    l(ChatColor.WHITE + " ");
                    l(ChatColor.WHITE + pluginName + ChatColor.GREEN + " Found a new version!");
                    l(ChatColor.WHITE + " ");
                    l(ChatColor.WHITE + "You Can Download here: https://www.spigotmc.org/resources/bedwarsrandomjoin.115020/");
                    l(ChatColor.WHITE + "If you do not want to check for updates, please set the update_checker in config.yml to false");
                    l(ChatColor.WHITE + " ");
                    l(ChatColor.WHITE + " ================================");
                }
            }
        });
    }

}
