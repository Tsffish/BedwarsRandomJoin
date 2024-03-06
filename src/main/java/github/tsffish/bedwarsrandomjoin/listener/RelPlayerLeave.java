package github.tsffish.bedwarsrandomjoin.listener;

import io.github.bedwarsrel.events.BedwarsPlayerLeaveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * A Addon for BedwarsRel, allow you to randomly join a game or choose any game in menu
 * github.com/Tsffish/BedwarsRandomJoin
 *
 * @author Tsffish
 */
public class RelPlayerLeave implements Listener {
    @EventHandler
    public void on(final BedwarsPlayerLeaveEvent event) {
            if (event.getPlayer() == null){
                return;
            }
            Player player = event.getPlayer();

            if (!player.isOnline()){
                return;
            }

            event.getGame().getPlayers().remove(player);
        }
    }
