package me.wikmor.playerstatsgui.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.ReflectionUtil;
import org.mineacademy.fo.model.SimpleExpansion;

/**
 * A sample placeholder hook utilizing PlaceholderAPI but also working without it.
 * <p>
 * Without PAPI: simply use {test1}
 * With PAPI: you need to prepend the variable with your plugin name, such as {chatcontrol_test1}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Placeholders extends SimpleExpansion {

	/**
	 * The singleton of this class
	 */
	@Getter
	private static final SimpleExpansion instance = new Placeholders();

	/**
	 * @see org.mineacademy.fo.model.SimpleExpansion#onReplace(org.bukkit.command.CommandSender, java.lang.String)
	 */
	@Override
	protected String onReplace(@NonNull final CommandSender sender, final String identifier) {
		final Player player = sender instanceof Player && ((Player) sender).isOnline() ? (Player) sender : null;

		final Statistics.PlayerData data = Statistics.from(player);
		final Category category = ReflectionUtil.lookupEnumSilent(Category.class, identifier.toUpperCase());

		if ("kdr".equals(identifier))
			return MathUtil.formatTwoDigits(data.getKDR());

		return category != null ? String.valueOf(data.get(category)) : null;
	}
}
