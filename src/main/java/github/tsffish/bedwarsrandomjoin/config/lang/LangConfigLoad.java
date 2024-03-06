package github.tsffish.bedwarsrandomjoin.config.lang;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static github.tsffish.bedwarsrandomjoin.config.misc.ErrorConfigHandler.er;
import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;
import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.finishLoadConfig;
import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.vauleIsNull;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class LangConfigLoad{
    private static final String name = "LangConfigLoad";
    private static final String reason = vauleIsNull;
    private static final BedwarsRandomJoin plugin = BedwarsRandomJoin.getInstance();
    public static YamlConfiguration config;

    public static void loadLangConfig(){

        File file = new File(plugin.getDataFolder(), "lang.yml");

        if (!file.exists()) {
                plugin.saveResource("lang.yml", false);
        }

        FileConfiguration c = YamlConfiguration.loadConfiguration(file);

        if (c.getStringList(LangConfigPath.path_update_tip) != null) {
            LangConfigHandler.update_tip = c.getStringList(LangConfigPath.path_update_tip);
        } else {
            sendError(LangConfigPath.path_update_tip);
        }

        if (c.getStringList(LangConfigPath.path_command_help) != null) {
            LangConfigHandler.command_help = c.getStringList(LangConfigPath.path_command_help);
        } else {
            sendError(LangConfigPath.path_command_help);
        }


        l("<" + name + "> " + finishLoadConfig);

    }
    private static void sendError(String path){
        er(name, path, reason);
    }
}