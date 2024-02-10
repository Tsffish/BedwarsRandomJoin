package github.tsffish.bedwarsrandomjoin.listener;

import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameManager;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static github.tsffish.bedwarsrandomjoin.config.main.MainConfigHandler.*;

public class PlayerClickHandler implements Listener
{
    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player){

        Player player = (Player) event.getWhoClicked();
        if (player == null || !player.isOnline()) return;

        Inventory inv = event.getClickedInventory();
        if (inv == null) return;

        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        Material itemType = itemStack.getType();

        GameManager gm = BedwarsRel.getInstance().getGameManager();
        if (gm == null) return;

        if (inv.getName().contains(menuTitle)) {
            event.setCancelled(true);
            if (itemType == gameCanJoinItemType){
             String itemName = itemStack.getItemMeta().getDisplayName();
             Game game = gm.getGame(itemName);
             if (game.getPlayers() == null)return;
             if (game.getState() == GameState.WAITING && game.getPlayers().size() < game.getMaxPlayers()) {

                 if (gm.getGameOfPlayer(player) != null && gm.getGameOfPlayer(player).getPlayers().contains(player)) {
                     gm.getGameOfPlayer(player).playerLeave(player, false);
                 }


                 game.playerJoins(player);
                 player.teleport(game.getLobby());
             }
            }
        }
    }
    }}
