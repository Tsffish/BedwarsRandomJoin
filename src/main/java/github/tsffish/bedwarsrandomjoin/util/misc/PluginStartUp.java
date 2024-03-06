package github.tsffish.bedwarsrandomjoin.util.misc;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import github.tsffish.bedwarsrandomjoin.command.CommandInfo;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Locale;

import static github.tsffish.bedwarsrandomjoin.config.main.MainConfigLoad.loadMainConfig;
import static github.tsffish.bedwarsrandomjoin.util.misc.ChatColor.*;
import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;
import static github.tsffish.bedwarsrandomjoin.util.misc.PluginState.*;
import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.msgline;
import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.pluginName;
import static github.tsffish.bedwarsrandomjoin.util.misc.bstats.StartMetrics.startMetrics;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class PluginStartUp {
    private static final BedwarsRandomJoin plugin = BedwarsRandomJoin.getInstance();
    public static void sendPluginStartUpInfo(){
        l(green + msgline);
        l(" ");
        l(white + pluginName + " " + aqua + pluginVersion());
        l(" ");
        l(white + "Author: " + yellow  + getAuthor());
        l(" ");
        l(green + msgline);
    }
    public static void regCommand(){
        plugin.getCommand("bwrj").setExecutor(new CommandInfo());
        plugin.getCommand("bwrj reload").setExecutor(new CommandInfo());
        plugin.getCommand("bwrj join").setExecutor(new CommandInfo());
        plugin.getCommand("bwrj open").setExecutor(new CommandInfo());
        l("Command registered");
    }
    public static void startup(){
        sendPluginStartUpInfo();
        File configFile = new File("spigot.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        boolean isBungeeEnabled = config.getBoolean("settings.bungeecord");
        setIsBungeeMode(isBungeeEnabled);

        setLanguage(Locale.getDefault().getLanguage());
        setServerVersion(plugin.getServer().getVersion());

        loadMainConfig(null, true);
        regCommand();

        if (!isDebug()) {startMetrics();}
    }
}
