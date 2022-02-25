package me.wikmor.playerstatsgui.command;

import me.wikmor.playerstatsgui.model.Category;
import me.wikmor.playerstatsgui.model.Permissions;
import me.wikmor.playerstatsgui.model.Statistics;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.settings.Lang;

import java.util.List;

/**
 * A sample standalone command.
 */
public final class AdminCommand extends SimpleCommand {

	/**
	 * Create a new standalone command /sample
	 */
	public AdminCommand() {
		super("adminstats|astats");

		setUsage("<args...>");
		setDescription("Main command for changing stats settings.");
		setMinArguments(1);
		setPermission(Permissions.Command.ADMIN_COMMANDS);

		// Uncomment to get rid of the automatically generated help text: https://i.imgur.com/Q79RKN0.png
		//setAutoHandleHelp(false);
		USE_MESSENGER = false;
		Messenger.ENABLED = false;
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommand#getMultilineUsageMessage()
	 */
	@Override
	protected String[] getMultilineUsageMessage() {
		return Lang.ofArray("Commands.Admin_Stats_Help");
	}

	/**
	 * Perform the main command logic.
	 */
	@Override
	protected void onCommand() {
		// /astats <set/add/remove> <player> <category> <amount>
		// /astats reset <player>

		final String param = args[0];

		checkArgs(2, "Please specify a player's name.");

		findOfflinePlayer(args[1], targetPlayer -> {

			if ("reset".equals(param)) {
				Statistics.getInstance().remove(targetPlayer);

				tell(Lang.of("Success.Stats_Reset").replace("{player}", targetPlayer.getName()));

			} else {
				checkBoolean("set".equals(param) || "add".equals(param) || "remove".equals(param),
						"Invalid parameter, use 'set', 'add', or 'remove', not {0}");

				checkArgs(4, "Usage: /{label} " + param + " {1} <category> <amount>");

				final Statistics.PlayerData playerData = Statistics.from(targetPlayer);
				final Category category = findEnum(Category.class, args[2], "Invalid category {2}, available: {available}");
				final int amount = findNumber(3, 0, Integer.MAX_VALUE, "The amount must be a whole number, got: {3}");
				final int oldAmount = playerData.get(category);
				final int newAmount = MathUtil.range("set".equals(param) ? amount : oldAmount + ("add".equals(param) ? amount : -amount), 0, Integer.MAX_VALUE);

				playerData.set(category, newAmount);
				tell(Lang.of("Success.Change_Applied").replace("{player}", targetPlayer.getName()));
			}
		});
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommand#tabComplete()
	 */
	@Override
	protected List<String> tabComplete() {
		switch (args.length) {
			case 1:
				return completeLastWord("set", "add", "remove", "reset");
			case 2:
				return completeLastWordPlayerNames();
			case 3:
				return completeLastWord(Category.values());
		}

		return NO_COMPLETE;
	}
}
