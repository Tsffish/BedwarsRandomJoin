package github.tsffish.bedwarsrandomjoin.command;

import github.tsffish.bedwarsrandomjoin.config.lang.LangConfigHandler;
import github.tsffish.bedwarsrandomjoin.util.MapInv;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameManager;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static github.tsffish.bedwarsrandomjoin.config.main.MainConfigLoad.loadMainConfig;
import static github.tsffish.bedwarsrandomjoin.util.misc.ColorString.t;
import static github.tsffish.bedwarsrandomjoin.util.misc.PluginState.getAuthor;
import static github.tsffish.bedwarsrandomjoin.util.misc.PluginState.pluginVersion;
import static github.tsffish.bedwarsrandomjoin.util.misc.StringMgr.pluginName;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class CommandInfo implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (!sender.isOp()) {
                showPluginInfo(sender);
            } else {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        reloadConfig(sender);
                        return true;
                    } else if (args[0].equalsIgnoreCase("open")) {
                        openMapInv(sender, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("join")) {
                        joinRandomGame(sender,args);
                        return true;
                    }
                }
                showHelpMess(sender);
            }
        } else {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig(sender);
                } else if (args[0].equalsIgnoreCase("open")) {
                    openMapInv(sender, args);
                } else if (args[0].equalsIgnoreCase("join")) {
                    joinRandomGame(sender,args);
                } else {
                    showHelpMess(sender);
                }
            } else {
                showHelpMess(sender);
            }
        }
        return true;
    }

    private void reloadConfig(CommandSender sender) {
        loadMainConfig(sender, false);
    }

    private void openMapInv(CommandSender sender, String[] args) {
        Player targetPlayer;
        if (args.length > 1) {
            targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Player not found or not online.");
                return;
            }
        } else {
            if (sender instanceof Player) {
                targetPlayer = (Player) sender;
            } else {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
                return;
            }
        }
        MapInv.openMapMenu(targetPlayer);
    }



    private void joinRandomGame(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player targetPlayer;
            if (args.length < 2) {
                targetPlayer = (Player) sender;
            } else {
                if (Bukkit.getPlayer(args[1]) != null) {
                    targetPlayer = Bukkit.getPlayer(args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found or not online.");
                    return;
                }
            }

            GameManager gm = BedwarsRel.getInstance().getGameManager();
            List<Game> gameList = new ArrayList<>();
            for (Game game : gm.getGames()) {
                if (game.getState() == GameState.WAITING) {
                    gameList.add(game);
                }
            }

            if (gameList.isEmpty()) {
                targetPlayer.sendMessage(ChatColor.RED + "There are no available games to join.");
                return;
            }

            Game selectedGame = null;
            int maxPlayers = 0;
            for (Game game : gameList) {
                if (selectedGame == null || game.getPlayers().size() < maxPlayers && game.getPlayers().size() != game.getMaxPlayers()) {
                    selectedGame = game;
                    maxPlayers = game.getPlayers().size();
                }
            }

            if (gm.getGameOfPlayer(targetPlayer).getPlayers().contains(targetPlayer)){
                gm.getGameOfPlayer(targetPlayer).playerLeave(targetPlayer, false);
            }

            gm.addGamePlayer(targetPlayer, selectedGame);
            selectedGame.playerJoins(targetPlayer);
            targetPlayer.teleport(selectedGame.getLobby());
        } else {

            Player targetPlayer;
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
                return;
            } else {
                if (Bukkit.getPlayer(args[1]) != null) {
                    targetPlayer = Bukkit.getPlayer(args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found or not online.");
                    return;
                }
            }

            GameManager gm = BedwarsRel.getInstance().getGameManager();
            List<Game> gameList = new ArrayList<>();
            for (Game game : gm.getGames()) {
                if (game.getState() == GameState.WAITING) {
                    gameList.add(game);
                }
            }

            if (gameList.isEmpty()) {
                targetPlayer.sendMessage(ChatColor.RED + "There are no available games to join.");
                return;
            }

            Game selectedGame = null;
            int maxPlayers = 0;
            for (Game game : gameList) {
                if (selectedGame == null || game.getPlayers().size() < maxPlayers && game.getPlayers().size() != game.getMaxPlayers()) {
                    selectedGame = game;
                    maxPlayers = game.getPlayers().size();
                }
            }

            if (gm.getGameOfPlayer(targetPlayer) != null) {
                if (gm.getGameOfPlayer(targetPlayer).getPlayers().contains(targetPlayer)) {
                    gm.getGameOfPlayer(targetPlayer).playerLeave(targetPlayer, false);
                }
            }
            gm.addGamePlayer(targetPlayer, selectedGame);
            selectedGame.playerJoins(targetPlayer);
            targetPlayer.teleport(selectedGame.getLobby());
        }
    }


    private void showHelpMess(CommandSender sender) {
        for (String string : LangConfigHandler.command_help){
            sender.sendMessage(t(string));
        }
    }

    private static void showPluginInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + " ================================");
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.WHITE + pluginName + " " + ChatColor.AQUA + pluginVersion());
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.WHITE + "Author: " + ChatColor.YELLOW + getAuthor());
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GREEN + " ================================");
    }
}
