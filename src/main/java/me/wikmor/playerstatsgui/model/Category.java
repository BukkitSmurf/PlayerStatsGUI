package me.wikmor.playerstatsgui.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
	KILLS,
	DEATHS,
	KILL_STREAK(false),
	TOP_KILL_STREAK;

	private final boolean savingToFile;

	Category() {
		this(true);
	}
}
