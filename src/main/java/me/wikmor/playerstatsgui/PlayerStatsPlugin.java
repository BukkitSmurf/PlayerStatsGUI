package me.wikmor.playerstatsgui;

import lombok.Getter;
import me.wikmor.playerstatsgui.command.AdminCommand;
import me.wikmor.playerstatsgui.command.UserCommandGroup;
import me.wikmor.playerstatsgui.listener.PlayerListener;
import me.wikmor.playerstatsgui.model.Placeholders;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.HookManager;
import org.mineacademy.fo.model.Variables;
import org.mineacademy.fo.plugin.SimplePlugin;

/**
 * PluginTemplate is a simple template you can use every time you make
 * a new plugin. This will save you time because you no longer have to
 * recreate the same skeleton and features each time.
 * <p>
 * It uses Foundation for fast and efficient development process.
 */
public final class PlayerStatsPlugin extends SimplePlugin {

	/**
	 * Automatically registers the main command group. A command group holds different
	 * commands, such as /chatcontrol is the main command group holding commands
	 * /chatcontrol mute, /chatcontrol clear etc.
	 */
	@Getter
	private final SimpleCommandGroup mainCommand = UserCommandGroup.getInstance();

	/**
	 * Automatically perform login ONCE when the plugin starts.
	 */
	@Override
	protected void onPluginStart() {
	}

	/**
	 * Automatically perform login when the plugin starts and each time it is reloaded.
	 */
	@Override
	protected void onReloadablesStart() {

		Messenger.ENABLED = false;
		SimpleCommand.USE_MESSENGER = false;

		// You can check for necessary plugins and disable loading if they are missing
		Valid.checkBoolean(HookManager.isVaultLoaded(), "You need to install Vault so that we can work with packets, offline player data, prefixes and groups.");

		// Load parts of the plugin
		//Packets.load();

		// Uncomment to load variables
		// Variable.loadVariables();

		// Register variables - PlaceholderAPI compatible
		Variables.addExpansion(Placeholders.getInstance());

		//
		// Add your own plugin parts to load automatically here
		//

		// Register commands
		registerCommand(new AdminCommand());

		//
		// Add your own commands here
		//

		// Register events
		registerEvents(PlayerListener.getInstance());

		//if (HookManager.isDiscordSRVLoaded())
		//	registerEvents(Discord.getInstance());

		//
		// Add your own events ere
		//
	}

	/* ------------------------------------------------------------------------------- */
	/* Static */
	/* ------------------------------------------------------------------------------- */

	/**
	 * Return the instance of this plugin, which simply refers to a static
	 * field already created for you in SimplePlugin but casts it to your
	 * specific plugin instance for your convenience.
	 *
	 * @return
	 */
	public static PlayerStatsPlugin getInstance() {
		return (PlayerStatsPlugin) SimplePlugin.getInstance();
	}
}
