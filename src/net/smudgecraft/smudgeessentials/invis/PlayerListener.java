package net.smudgecraft.smudgeessentials.invis;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener
  implements Listener
{

  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    InvisPlayer ip = new InvisPlayer(player);

    if (player.hasPermission("invis.use"))
    {
      event.setJoinMessage("");
      for (Player p : SmudgeEssentials.plugin.getServer().getOnlinePlayers())
      {
        if (p.hasPermission("invis.see"))
          continue;
        p.hidePlayer(player);
      }

      ip.setIsInvisible(true);
    }
    else if (!player.hasPermission("invis.see"))
    {
      for (InvisPlayer tIp : Invis.invisPlayers)
      {
        if (tIp.getIsInvisible())
          player.hidePlayer(tIp.getPlayer());
      }
    }
    Invis.invisPlayers.add(ip);
  }

  @EventHandler
  public void onPlayerQuitEvent(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    InvisPlayer ip = Invis.getInvisPlayer(player);
   Invis.invisPlayers.remove(ip);
  }
}