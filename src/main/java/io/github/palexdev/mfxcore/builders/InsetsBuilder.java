/*
 * Copyright (C) 2022 Parisi Alessandro
 * This file is part of MFXCore (https://github.com/palexdev/MFXCore).
 *
 * MFXCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MFXCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MFXCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.mfxcore.builders;

import javafx.geometry.Insets;

/**
 * Convenience class to build {@link Insets} objects.
 */
public class InsetsBuilder {

	//================================================================================
	// Constructors
	//================================================================================
	private InsetsBuilder() {}

	//================================================================================
	// Static Methods
	//================================================================================
	public static Insets all(double topRightBottomLeft) {
		return new Insets(topRightBottomLeft);
	}

	public static Insets none() {
		return Insets.EMPTY;
	}

	public static Insets top(double top) {
		return new Insets(top, 0, 0, 0);
	}

	public static Insets right(double right) {
		return new Insets(0, right, 0, 0);
	}

	public static Insets bottom(double bottom) {
		return new Insets(0, 0, bottom, 0);
	}

	public static Insets left(double left) {
		return new Insets(0, 0, 0, left);
	}

	public static Insets of(double top, double right) {
		return new Insets(top, right, 0, 0);
	}

	public static Insets of(double top, double right, double bottom) {
		return new Insets(top, right, bottom, 0);
	}

	public static Insets of(double top, double right, double bottom, double left) {
		return new Insets(top, right, bottom, left);
	}
}
