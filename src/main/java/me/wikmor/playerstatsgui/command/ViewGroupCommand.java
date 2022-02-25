package me.wikmor.playerstatsgui.command;

import me.wikmor.playerstatsgui.menu.MenuViewStats;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.List;

/**
 * A sample command belonging to a command group.
 */
public final class ViewGroupCommand extends SimpleSubCommand {

	/**
	 * Create a new sample group command, such as /sample menu.
	 */
	public ViewGroupCommand() {
		super("view");

		setUsage("[player]");
		setDescription("Open the menu showing the player's stats.");
	}

	/**
	 * Perform the main command logic.
	 */
	@Override
	protected void onCommand() {
		checkConsole();

		if (args.length == 0)
			new MenuViewStats(getPlayer()).displayTo(getPlayer());

		if (args.length == 1)
			findOfflinePlayer(args[0], targetPlayer -> new MenuViewStats(targetPlayer).displayTo(getPlayer()));
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommand#tabComplete()
	 */
	@Override
	protected List<String> tabComplete() {
		return completeLastWordPlayerNames();
	}
}
