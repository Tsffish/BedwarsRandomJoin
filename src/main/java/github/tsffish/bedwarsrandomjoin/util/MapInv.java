package github.tsffish.bedwarsrandomjoin.util;

import github.tsffish.bedwarsrandomjoin.BedwarsRandomJoin;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameManager;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

import static github.tsffish.bedwarsrandomjoin.config.main.MainConfigHandler.*;
/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class MapInv {
    private static final Plugin plugin = JavaPlugin.getPlugin(BedwarsRandomJoin.class);
    private static Inventory mapInv;
    private static Set<String> addedGames = new HashSet<>();
    public static void loadMapInv(long time) {

        new BukkitRunnable() {
            @Override
            public void run() {

                BedwarsRel bedwarsRel = BedwarsRel.getInstance();
                GameManager gameManager = bedwarsRel.getGameManager();

                if (mapInv == null) {
                    mapInv = Bukkit.getServer().createInventory(null, menuRow * 9, menuTitle);
                }else {
                    mapInv.clear();
                }

                addedGames.clear();

                for (Game game : gameManager.getGames()) {
                    if (game != null) {
                        int current = game.getPlayers().size();
                        String gameName = game.getName();
                        if (game.getState() == GameState.WAITING) {
                            if (current < game.getMaxPlayers()) {
                                if (!addedGames.contains(gameName)) {
                                    ItemStack i1 = new ItemStack(gameCanJoinItemType, current);
                                    ItemMeta i1s = i1.getItemMeta();
                                    i1s.setDisplayName(gameName);
                                    i1.setItemMeta(i1s);
                                    mapInv.addItem(i1);
                                    addedGames.add(gameName);
                                }
                            } else if (game.isFull() && !addedGames.contains(gameName)) {
                                ItemStack i2 = new ItemStack(gameFullItemType, 1);
                                ItemMeta i2s = i2.getItemMeta();
                                i2s.setDisplayName(gameName);
                                i2.setItemMeta(i2s);
                                mapInv.addItem(i2);
                                addedGames.add(gameName);
                            }

                        } else if (game.getState() == GameState.STOPPED && !addedGames.contains(gameName)) {
                            ItemStack i3 = new ItemStack(gameStopItemType, 1);
                            ItemMeta i3s = i3.getItemMeta();
                            i3s.setDisplayName(gameName);
                            i3.setItemMeta(i3s);
                            mapInv.addItem(i3);
                            addedGames.add(gameName);
                        } else if (game.getState() == GameState.RUNNING && !addedGames.contains(gameName)) {
                            ItemStack i4 = new ItemStack(gameRuningItemType, 1);
                            ItemMeta i4s = i4.getItemMeta();
                            i4s.setDisplayName(gameName);
                            i4.setItemMeta(i4s);
                            mapInv.addItem(i4);
                            addedGames.add(gameName);
                        }
                    }
                }
            }
        }.runTaskLater(plugin, time);
    }

    public static void openMapMenu(Player player) {
        if (player == null || !player.isOnline()) return;
        loadMapInv(0L);

        player.openInventory(mapInv);
    }
}
