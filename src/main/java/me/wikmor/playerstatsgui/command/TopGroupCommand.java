package me.wikmor.playerstatsgui.command;

import me.wikmor.playerstatsgui.menu.MenuTopStats;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class TopGroupCommand extends SimpleSubCommand {

	public TopGroupCommand() {
		super("top");

		setDescription("Open the leaderboard menu.");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		new MenuTopStats().displayTo(getPlayer());
	}
}
