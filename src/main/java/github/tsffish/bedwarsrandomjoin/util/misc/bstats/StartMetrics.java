package github.tsffish.bedwarsrandomjoin.util.misc.bstats;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import github.tsffish.bedwarsrandomjoin.util.misc.PluginState;
import org.bstats.bukkit.Metrics;

import static github.tsffish.bedwarsrandomjoin.util.misc.MessSender.le;
import static github.tsffish.bedwarsrandomjoin.util.misc.PluginState.isBungeeMode;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class StartMetrics {
    private static final BedwarsRandomJoin plugin = BedwarsRandomJoin.getInstance();
    private static final String className = "StartMetrics";
    public static void startMetrics(){
        try {
            Metrics metrics = new Metrics(plugin, 20967);

            metrics.addCustomChart(new Metrics.SimplePie("server_version", () -> plugin.getServer().getVersion()));
            metrics.addCustomChart(new Metrics.SimplePie("server_language", PluginState::language));
            metrics.addCustomChart(new Metrics.SimplePie("server_auth_mode", () -> String.valueOf(plugin.getServer().getOnlineMode())));
            metrics.addCustomChart(new Metrics.SimplePie("bungeecord_enabled", () -> String.valueOf(isBungeeMode())));
            metrics.addCustomChart(new Metrics.SimplePie("plugin_version", PluginState::pluginVersion));
            metrics.addCustomChart(new Metrics.SimplePie("os_name", () -> System.getProperty("os.name")));

        } catch (Exception e) {
            le(className, e);
        }
    }
}
