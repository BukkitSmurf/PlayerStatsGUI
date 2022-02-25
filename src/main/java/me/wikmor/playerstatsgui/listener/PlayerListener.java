package me.wikmor.playerstatsgui.listener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.wikmor.playerstatsgui.model.Category;
import me.wikmor.playerstatsgui.model.Statistics;
import me.wikmor.playerstatsgui.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mineacademy.fo.model.Variables;

/**
 * A sample listener for events.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerListener implements Listener {

	/**
	 * The singleton instance
	 */
	@Getter
	private static final PlayerListener instance = new PlayerListener();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		event.setFormat(Variables.replace(event.getFormat(), event.getPlayer()));
	}

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player player = event.getEntity();
		
		if (!Settings.WORLDS.contains(player.getWorld().getName()))
			return;

		final Statistics.PlayerData data = Statistics.from(player);

		data.add(Category.DEATHS);
		data.set(Category.KILL_STREAK, 0);

		final Player killer = player.getKiller();

		if (killer != null && !killer.equals(player)) {
			final Statistics.PlayerData killerData = Statistics.from(killer);

			killerData.add(Category.KILLS);
			killerData.add(Category.KILL_STREAK);
		}
	}
}
