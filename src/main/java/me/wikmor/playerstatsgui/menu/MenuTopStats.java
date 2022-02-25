package me.wikmor.playerstatsgui.menu;

import me.wikmor.playerstatsgui.model.Category;
import me.wikmor.playerstatsgui.model.Statistics;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompColor;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Arrays;

public final class MenuTopStats extends MenuPagged<Category> {

	public MenuTopStats() {
		super(Arrays.asList(Category.values()));
	}

	@Override
	protected ItemStack convertToItemStack(final Category category) {
		return ItemCreator.of(CompMaterial.WHITE_WOOL,
						"Open " + ItemUtil.bountifyCapitalized(category) + " Category",
						"",
						"Click to browse top",
						"players in this category.")
				.color(CompColor.values()[category.ordinal()])
				.build().make();
	}

	@Override
	protected void onPageClick(final Player player, final Category item, final ClickType click) {
		new MenuTopStatsIndividual(item).displayTo(player);
	}

	public final class MenuTopStatsIndividual extends MenuPagged<Statistics.PlayerData> {

		protected MenuTopStatsIndividual(final Category sortBy) {
			super(MenuTopStats.this, Statistics.getInstance().getSorted(sortBy));

			this.setTitle("Top Stats By " + ItemUtil.bountifyCapitalized(sortBy));
		}

		@Override
		protected ItemStack convertToItemStack(final Statistics.PlayerData playerData) {
			return playerData.toItem();
		}

		@Override
		protected void onPageClick(final Player player, final Statistics.PlayerData item, final ClickType click) {
			this.animateTitle("Clicked on the " + item.getPlayerName() + "'s data");
		}
	}
}