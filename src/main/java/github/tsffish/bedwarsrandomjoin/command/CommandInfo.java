package github.tsffish.bedwarsrandomjoin.command;

import github.tsffish.bedwarsrandomjoin.config.main.MainConfigLoad;
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
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

import static github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin.*;

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
                // 未知的命令或者没有提供参数，显示帮助信息
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
                    // 未知的命令，显示帮助信息
                    showHelpMess(sender);
                }
            } else {
                // 参数为空，显示帮助信息
                showHelpMess(sender);
            }
        }
        return true;
    }

    private void reloadConfig(CommandSender sender) {
        MainConfigLoad.loadMainConfig(sender, false);
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
        // 执行玩家相关的逻辑
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

            if (gm.getGameOfPlayer(targetPlayer).getPlayers().contains(targetPlayer)){
                gm.getGameOfPlayer(targetPlayer).playerLeave(targetPlayer, false);
            }

            gm.addGamePlayer(targetPlayer, selectedGame);
            selectedGame.playerJoins(targetPlayer);
            targetPlayer.teleport(selectedGame.getLobby());
        }
    }


    private void showHelpMess(CommandSender sender) {
        PluginManager pm = Bukkit.getPluginManager();
        if (pm.getPlugin("BedwarsRel") != null) {
            sender.sendMessage(ChatColor.GREEN + " ================================");
            helpMsg(sender);
            sender.sendMessage(ChatColor.GREEN + " ================================");
        } else {
            sender.sendMessage(ChatColor.RED + " ================================");
            helpMsg(sender);
            sender.sendMessage(ChatColor.RED + " ================================");
        }
    }

    private static void showPluginInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + " ================================");
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.WHITE + pluginName + " " + ChatColor.AQUA + pluginVersion);
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.WHITE + "Author: " + ChatColor.YELLOW + author);
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GREEN + " ================================");
    }

    private static void helpMsg(CommandSender sender) {
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.WHITE + pluginName + " " + ChatColor.AQUA + "Commands:");
        sender.sendMessage(" ");
        sender.sendMessage(" " + ChatColor.WHITE + "/bwrj" + ChatColor.YELLOW + " Display this help information.");
        sender.sendMessage(" " + ChatColor.WHITE + "/bwrj reload" + ChatColor.YELLOW + " Reload configuration file.");
        sender.sendMessage(" " + ChatColor.WHITE + "/bwrj open {player}" + ChatColor.YELLOW + " Open menu for a specific player or open menu for you.");
        sender.sendMessage(" " + ChatColor.WHITE + "/bwrj join {player}" + ChatColor.YELLOW + " RandomJoin for a specific player or randomjoin for you.");
        sender.sendMessage(" ");
    }
}
