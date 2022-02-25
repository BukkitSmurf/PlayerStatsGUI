package me.wikmor.playerstatsgui.menu;

import me.wikmor.playerstatsgui.model.Statistics;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public final class MenuViewStats extends Menu {
	private OfflinePlayer targetPlayer;

	public MenuViewStats(final OfflinePlayer targetPlayer) {
		this.targetPlayer = targetPlayer;

		this.setTitle("&a" + targetPlayer.getName() + "'s Stats");
		this.setSize(9 * 3);
	}

	@Override
	public ItemStack getItemAt(final int slot) {
		if (slot == this.getCenterSlot()) {
			return Statistics.from(this.targetPlayer).toItem();
		}

		return ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, "").build().make();
	}
}