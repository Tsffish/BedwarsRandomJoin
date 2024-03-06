package github.tsffish.bedwarsrandomjoin;

import org.bukkit.plugin.java.JavaPlugin;

import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;
import static github.tsffish.bedwarsrandomjoin.util.misc.PluginStartUp.startup;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */

public class BedwarsRandomJoin extends JavaPlugin {
    private static BedwarsRandomJoin instance;
    public static BedwarsRandomJoin getInstance(){
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        startup();
    }
    @Override
    public void onDisable ()
    {
        l("Disabled.");
    }

}
