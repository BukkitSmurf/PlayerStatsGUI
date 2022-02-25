package me.wikmor.playerstatsgui.command;

import me.wikmor.playerstatsgui.model.Category;
import me.wikmor.playerstatsgui.model.Statistics;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.settings.Lang;

import java.util.List;

public final class SetGroupCommand extends SimpleSubCommand {

	public SetGroupCommand() {
		super("set");

		setMinArguments(3);
		setUsage("<player> <category> <amount>");
		setDescription("Set a player's stats.");

		setAutoHandleHelp(false);
	}

	@Override
	protected void onCommand() {
		checkArgs(1, "Please specify a player's name.");

		findOfflinePlayer(args[0], targetPlayer -> {
			final Statistics.PlayerData playerData = Statistics.from(targetPlayer);
			final Category category = findEnum(Category.class, args[1], "Invalid category {1}, available: {available}");
			final int amount = findNumber(2, 0, Integer.MAX_VALUE, "The amount must be a whole number, got: {2}");

			playerData.set(category, amount);
			tell(Lang.of("Success.Change_Applied").replace("{player}", targetPlayer.getName()));
		});
	}

	@Override
	protected List<String> tabComplete() {
		switch (args.length) {
			case 1:
				return completeLastWordPlayerNames();
			case 2:
				return completeLastWord(Category.values());
		}

		return NO_COMPLETE;
	}
}
