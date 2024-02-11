package github.tsffish.bedwarsrandomjoin.config.main;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import github.tsffish.bedwarsrandomjoin.listener.PlayerClickHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin.checkUpdate;
import static github.tsffish.bedwarsrandomjoin.config.main.MainConfigHandler.*;
import static github.tsffish.bedwarsrandomjoin.config.misc.ErrorConfigHandler.er;
import static github.tsffish.bedwarsrandomjoin.util.MapInv.loadMapInv;
import static github.tsffish.bedwarsrandomjoin.util.misc.ColorString.t;
import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;
import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.le;

public class MainConfigLoad{
    private static final Plugin plugin = JavaPlugin.getPlugin(BedwarsRandomJoin.class);
    private static final String name = "MainConfigLoad";
    private static final String reason = "vaule is null";
    public static void loadMainConfig(CommandSender executer, boolean firstload) {

        plugin.saveDefaultConfig();
        c = plugin.getConfig();

        if (c == null) {
            le("MainConfigLoad","Unable to find configuration file");
            if (executer != null)
            {
                executer.sendMessage("Unable to find configuration file");
            }
        } else {

            plugin.reloadConfig();

            if (c.getString(MainConfigPath.path_messreloadnow) != null) {
                messreloadnow = c.getString(MainConfigPath.path_messreloadnow);
            } else {
                messreloadnow = t("&bBedwarsRandomJoin &7>> &eReloading configuration file");
                sendError(MainConfigPath.path_messreloadnow);
            }

            if (c.getString(MainConfigPath.path_messreloadsucc) != null) {
                messreloadsucc = c.getString(MainConfigPath.path_messreloadsucc);
            } else {
                messreloadsucc = t("&bBedwarsRandomJoin &7>> &aSuccessfully reloaded configuration file");
                sendError(MainConfigPath.path_messreloadsucc);
            }

            if (!firstload) {
                if (executer != null) {
                    executer.sendMessage(t(messreloadnow));
                }
            }

            if (firstload){
                if (c.getString(MainConfigPath.path_update_checker) != null) {
                    boolean update_checker = c.getBoolean(MainConfigPath.path_update_checker);
                    if (update_checker) {
                        checkUpdate(105616);
                    }
                } else {
                    er(name, MainConfigPath.path_update_checker, reason);
                    checkUpdate(105616);
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

            loadMapInv(100L);

            l("<MainConfigLoad> Finish Load Config");

            if (!firstload) {
                if (executer != null) {
                    executer.sendMessage(t(messreloadsucc));
                }
            }else {
                PluginManager pm = Bukkit.getPluginManager();

                pm.registerEvents(new PlayerClickHandler(), plugin);
                l(ChatColor.GREEN + "BedwarsRel found, related support enable");
            }
        }
    }
    private static void sendError(String path){
        er(name, path, reason);
    }
}