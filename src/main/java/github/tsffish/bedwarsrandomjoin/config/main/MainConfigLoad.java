package github.tsffish.bedwarsrandomjoin.config.main;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import github.tsffish.bedwarsrandomjoin.listener.PlayerClick;
import github.tsffish.bedwarsrandomjoin.listener.RelPlayerLeave;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

import static github.tsffish.bedwarsrandomjoin.config.lang.LangConfigLoad.loadLangConfig;
import static github.tsffish.bedwarsrandomjoin.config.main.MainConfigHandler.*;
import static github.tsffish.bedwarsrandomjoin.config.misc.ErrorConfigHandler.er;
import static github.tsffish.bedwarsrandomjoin.util.MapInv.loadMapInv;
import static github.tsffish.bedwarsrandomjoin.util.misc.ColorString.t;
import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;
import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.pluginName;
import static github.tsffish.bedwarsrandomjoin.util.misc.update.StartCheck.checkUpdate;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class MainConfigLoad{
    private static final Plugin plugin = JavaPlugin.getPlugin(BedwarsRandomJoin.class);
    private static final String name = "MainConfigLoad";
    private static final String reason = "vaule is null";
    public static void loadMainConfig(CommandSender executer, boolean firstload) {

        File file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }

        FileConfiguration c = YamlConfiguration.loadConfiguration(file);

            if (c.getString(MainConfigPath.path_messreloadnow) != null) {
                messreloadnow = c.getString(MainConfigPath.path_messreloadnow);
            } else {
                messreloadnow = t("&b" + pluginName + " &7>> &eReloading configuration file");
                sendError(MainConfigPath.path_messreloadnow);
            }

            if (c.getString(MainConfigPath.path_messreloadsucc) != null) {
                messreloadsucc = c.getString(MainConfigPath.path_messreloadsucc);
            } else {
                messreloadsucc = t("&b" + pluginName + " &7>> &aSuccessfully reloaded configuration file");
                sendError(MainConfigPath.path_messreloadsucc);
            }

            if (!firstload) {
                if (executer != null) {
                    executer.sendMessage(t(messreloadnow));
                }
            }

            if (c.getString(MainConfigPath.path_gameCanJoinItemType) != null) {
                gameCanJoinItemType = Material.getMaterial(c.getString(MainConfigPath.path_gameCanJoinItemType));
            } else {
                sendError(MainConfigPath.path_gameCanJoinItemType);
            }

            if (c.getString(MainConfigPath.path_gameRuningItemType) != null) {
                gameRuningItemType = Material.getMaterial(c.getString(MainConfigPath.path_gameRuningItemType));
            } else {
                sendError(MainConfigPath.path_gameRuningItemType);
            }

            if (c.getString(MainConfigPath.path_gameFullItemType) != null) {
                gameFullItemType = Material.getMaterial(c.getString(MainConfigPath.path_gameFullItemType));
            } else {
                sendError(MainConfigPath.path_gameFullItemType);
            }


            if (c.getString(MainConfigPath.path_gameStopItemType) != null) {
                gameStopItemType = Material.getMaterial(c.getString(MainConfigPath.path_gameStopItemType));
            } else {
                sendError(MainConfigPath.path_gameStopItemType);
            }

            if (c.getString(MainConfigPath.path_menuTitle) != null) {
                menuTitle = c.getString(MainConfigPath.path_menuTitle);
            } else {
                sendError(MainConfigPath.path_menuTitle);
            }

            if (c.getString(MainConfigPath.path_menuRow) != null) {
                menuRow = c.getInt(MainConfigPath.path_menuRow);
            } else {
                sendError(MainConfigPath.path_menuRow);
            }

            loadLangConfig();
            loadMapInv(100L);

            l("<" +name+"> Finish Load Config");


        new BukkitRunnable() {
            @Override
            public void run() {
                if (firstload) {
                    if (c.getString(MainConfigPath.path_update_checker) != null) {
                        boolean update_checker = c.getBoolean(MainConfigPath.path_update_checker);
                        if (update_checker) {
                            checkUpdate(115020);
                        }
                    } else {
                        er(name, MainConfigPath.path_update_checker, reason);
                        checkUpdate(115020);
                    }
                }
            }
        }.runTaskLater(plugin,40L);


            if (!firstload) {
                if (executer != null) {
                    executer.sendMessage(t(messreloadsucc));
                }
            }else {
                PluginManager pm = Bukkit.getPluginManager();

                pm.registerEvents(new PlayerClick(), plugin);
                pm.registerEvents(new RelPlayerLeave(), plugin);
            }
        }
    private static void sendError(String path){
        er(name, path, reason);
    }
}