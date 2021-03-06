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

package io.github.palexdev.mfxcore.base.properties.styleable;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableFloatProperty;
import javafx.css.StyleOrigin;
import javafx.css.Styleable;

public class StyleableFloatProperty extends SimpleStyleableFloatProperty {

	public StyleableFloatProperty(CssMetaData<? extends Styleable, Number> cssMetaData) {
		super(cssMetaData);
	}

	public StyleableFloatProperty(CssMetaData<? extends Styleable, Number> cssMetaData, Float initialValue) {
		super(cssMetaData, initialValue);
	}

	public StyleableFloatProperty(CssMetaData<? extends Styleable, Number> cssMetaData, Object bean, String name) {
		super(cssMetaData, bean, name);
	}

	public StyleableFloatProperty(CssMetaData<? extends Styleable, Number> cssMetaData, Object bean, String name, Float initialValue) {
		super(cssMetaData, bean, name, initialValue);
	}

	@Override
	public StyleOrigin getStyleOrigin() {
		return StyleOrigin.USER_AGENT;
	}
}
