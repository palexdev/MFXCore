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

package io.github.palexdev.mfxcore.utils;

import java.util.List;
import java.util.Random;

/**
 * Set of utilities for random ops.
 */
public class RandomUtils {
	public static final Random random = new Random(System.currentTimeMillis());

	private RandomUtils() {
	}

	/**
	 * @return a random value from the given array
	 */
	public static <T> T randFromArray(T[] array) {
		int index = random.nextInt(array.length);
		return array[index];
	}

	/**
	 * @return a random value from the given list
	 */
	public static <T> T randFromList(List<T> list) {
		int index = random.nextInt(list.size());
		return list.get(index);
	}
}
