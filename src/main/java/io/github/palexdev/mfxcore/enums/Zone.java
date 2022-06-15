package io.github.palexdev.mfxcore.enums;

public enum Zone {
	TOP_RIGHT, TOP_CENTER, TOP_LEFT,
	BOTTOM_RIGHT, BOTTOM_CENTER, BOTTOM_LEFT,
	CENTER_RIGHT, CENTER_LEFT,
	NONE, ALL;

	public static boolean isRight(Zone zone) {
		return zone == TOP_RIGHT || zone == CENTER_RIGHT || zone == BOTTOM_RIGHT;
	}

	public static boolean isLeft(Zone zone) {
		return zone == TOP_LEFT || zone == CENTER_LEFT || zone == BOTTOM_LEFT;
	}

	public static boolean isTop(Zone zone) {
		return zone == TOP_LEFT || zone == TOP_CENTER || zone == TOP_RIGHT;
	}

	public static boolean isBottom(Zone zone) {
		return zone == BOTTOM_RIGHT || zone == BOTTOM_CENTER || zone == BOTTOM_LEFT;
	}
}
