package github.tsffish.bedwarsrandomjoin.util.misc;

import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.pluginName;

/**
 * A Addon for BedwarsRel, Added some features to BedwarsRel
 * github.com/Tsffish/BedwarsKit
 *
 * @author Tsffish
 */
public class PluginState {
    private static final String pluginVersion = "1.0.4";
    public static String pluginVersion(){
        return pluginVersion;
    }
    public static String getPluginNameConsole = "[" + pluginName + "]";
    private static final String pluginNameConsole = "[" + pluginName + "]";
    public static String pluginNameConsole(){
        return pluginNameConsole;
    }
    private static final String author = "Tsffish";
    public static String getAuthor(){
        return author;
    }
    private static boolean isDebug = false;
    public static boolean isDebug(){
        return isDebug;
    }
    public static void changeIsDebug(){
        isDebug = !isDebug;
    }
    private static boolean isLastestVersion;
    public static boolean isLastestVersion(){
        return isLastestVersion;
    }
    public static void setIsLastestVersion(boolean setTo){
        isLastestVersion = setTo;
    }
    private static final int spigotId = 115020;
    public static int spigotId(){
        return spigotId;
    }
    public static String language;
    public static String language() {
        return language;
    }
    public static void setLanguage(String setTo) {
        language = setTo;
    }
    private static boolean isBungeeMode;
    public static boolean isBungeeMode() {
        return isBungeeMode;
    }
    public static void setIsBungeeMode(boolean setTo) {
        isBungeeMode = setTo;
    }
    private static String serverVersion;
    public static String serverVersion(){
        return serverVersion;
    }
    public static void setServerVersion(String setTo){
        serverVersion = setTo;
    }
}
