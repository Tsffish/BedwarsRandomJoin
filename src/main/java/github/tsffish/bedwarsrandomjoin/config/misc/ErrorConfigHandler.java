package github.tsffish.bedwarsrandomjoin.config.misc;

import org.bukkit.Bukkit;

import static github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin.pluginNameConsole;

public class ErrorConfigHandler {
    private static final String mess = "An error occurred while attempting to load -> ";

    public static void er(String name, String path ,String exception){
        Bukkit.getLogger().warning(pluginNameConsole + " <" + name + "> " + mess + path + " : " + exception);
    }
    public static void er(String name, String path ,Exception exception){
        Bukkit.getLogger().warning(pluginNameConsole + " <" + name + "> " + mess + path + " : " + exception);
    }
}
