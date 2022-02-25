package me.wikmor.playerstatsgui.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.ReflectionUtil;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.*;

public class Statistics extends YamlConfig {

	@Getter
	private static final Statistics instance = new Statistics();

	private Map<UUID, PlayerData> playerDataMap = new HashMap<>();

	private Statistics() {
		this.loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	@Override
	protected void onLoadFinish() {
		this.playerDataMap = this.getMap("Statistics", UUID.class, PlayerData.class);
	}

	@Override
	protected SerializedMap serialize() {
		return SerializedMap.of("Statistics", this.playerDataMap);
	}

	public List<PlayerData> getSorted(final Category sortBy) {
		final List<PlayerData> sortedData = new ArrayList<>(this.playerDataMap.values());
		sortedData.sort((firstData, secondData) -> Integer.compare(secondData.get(sortBy), firstData.get(sortBy)));

		return sortedData;
	}

	public void remove(final OfflinePlayer player) {
		this.playerDataMap.remove(player.getUniqueId());

		this.save();
	}

	private PlayerData lookupData(final OfflinePlayer player) {
		PlayerData data = this.playerDataMap.get(player.getUniqueId());

		if (data == null) {
			data = new PlayerData(player.getName());

			this.playerDataMap.put(player.getUniqueId(), data);
		}

		return data;
	}

	public static PlayerData from(final OfflinePlayer player) {
		return instance.lookupData(player);
	}

	@RequiredArgsConstructor
	public final static class PlayerData implements ConfigSerializable {

		@Getter
		private final String playerName;
		private final Map<Category, Integer> data = new HashMap<>();

		public void add(final Category category) {
			this.set(category, this.get(category) + 1);
		}

		public void set(final Category category, final int amount) {
			this.data.put(category, amount);

			if (category == Category.KILL_STREAK && this.get(Category.TOP_KILL_STREAK) < amount)
				this.data.put(Category.TOP_KILL_STREAK, amount);

			if (category.isSavingToFile())
				instance.save();
		}

		public int get(final Category category) {
			return this.data.getOrDefault(category, 0);
		}

		public double getKDR() {
			final double kills = this.get(Category.KILLS);
			final double deaths = this.get(Category.DEATHS);

			final double ratio = Math.round(((kills / deaths) + Double.MIN_NORMAL) * 10);

			if (deaths > 1)
				return ratio / 10;

			return kills;
		}

		public ItemStack toItem() {
			final List<String> lores = new ArrayList<>();

			for (final Category category : Category.values())
				lores.add("&c" + ItemUtil.bountifyCapitalized(category) + ": &7" + this.get(category));

			lores.add("&cKDR: &7" + this.getKDR());

			return ItemCreator.of(CompMaterial.PLAYER_HEAD, this.playerName)
					.lores(lores)
					.skullOwner(this.playerName)
					.build().make();
		}

		@Override
		public SerializedMap serialize() {
			final SerializedMap map = new SerializedMap();

			for (final Map.Entry<Category, Integer> entry : this.data.entrySet()) {
				final Category category = entry.getKey();

				if (category.isSavingToFile())
					map.put(category.toString(), entry.getValue());
			}

			map.put("Name", this.playerName);

			return map;
		}

		public static PlayerData deserialize(final SerializedMap map) {
			final PlayerData playerData = new PlayerData((String) map.remove("Name"));

			for (final Map.Entry<String, Object> entry : map.entrySet())
				playerData.data.put(ReflectionUtil.lookupEnum(Category.class, entry.getKey()), Integer.parseInt(entry.getValue().toString()));

			return playerData;
		}
	}
}
