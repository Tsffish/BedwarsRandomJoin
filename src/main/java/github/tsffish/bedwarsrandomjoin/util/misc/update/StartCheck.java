package github.tsffish.bedwarsrandomjoin.util.misc.update;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import org.bukkit.plugin.java.JavaPlugin;

import static github.tsffish.bedwarsrandomjoin.config.lang.LangConfigHandler.update_tip;
import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.l;
import static github.tsffish.bedwarsrandomjoin.util.misc.PluginState.*;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class StartCheck {
    public static void checkUpdate(int resId)
    {
        new UpdateChecker(JavaPlugin.getPlugin(BedwarsRandomJoin.class), resId).getVersion(version ->
        {
            setIsLastestVersion(pluginVersion().equals(version));
            if (!isLastestVersion()){
                if (update_tip != null && !update_tip.isEmpty()){
                    for (String list : update_tip){
                        l(list);
                    }
                }
            }
        });
    }
}
