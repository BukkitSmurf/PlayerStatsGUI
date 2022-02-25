package me.wikmor.playerstatsgui.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.wikmor.playerstatsgui.model.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.command.DebugCommand;
import org.mineacademy.fo.command.PermsCommand;
import org.mineacademy.fo.command.ReloadCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.SimpleComponent;

import java.util.List;

/**
 * A sample command group. A command group is a collection of commands,
 * such as for the Boss plugin, the command group is /boss, where
 * subcommands can be "spawn", "remove" etc. Example:
 * <p>
 * /boss spawn
 * /boss remove
 * (etc..)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCommandGroup extends SimpleCommandGroup {

	/**
	 * The singleton of this class
	 */
	@Getter
	private final static SimpleCommandGroup instance = new UserCommandGroup();

	/**
	 * @see org.mineacademy.fo.command.SimpleCommandGroup#getHeaderPrefix()
	 */
	@Override
	protected String getHeaderPrefix() {
		return "" + ChatColor.DARK_RED + ChatColor.BOLD;
	}

	// Change this to edit the default message shown when you type the main
	// command group label, here: /plugintemplate
	@Override
	protected List<SimpleComponent> getNoParamsHeader(final CommandSender sender) {
		return super.getNoParamsHeader(sender);
	}

	// Change this to remove "Visit MineAcademy" link we by default have for our plugins
	@Override
	protected String getCredits() {
		return super.getCredits();
	}

	// Change this to edit the messages at the top of our help command, defaults to
	// typing "/plugintemplate ?" or "/plugintemplate help" (you can change ?/help by
	// overriding "getHelpLabel()")
	@Override
	protected String[] getHelpHeader() {
		return super.getHelpHeader();
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommandGroup#registerSubcommands()
	 */
	@Override
	protected void registerSubcommands() {

		// Register a sample command for this group
		registerSubcommand(new ViewGroupCommand());
		registerSubcommand(new TopGroupCommand());

		// Register the premade commands from Foundation
		registerSubcommand(new DebugCommand("playerstatsgui.command.debug"));
		registerSubcommand(new ReloadCommand("playerstatsgui.command.reload"));
		registerSubcommand(new PermsCommand(Permissions.class, "playerstatsgui.command.perms"));


	}
}
