package io.github.palexdev.mfxcore.base.properties;

import javafx.beans.property.SimpleObjectProperty;

public class CharProperty extends SimpleObjectProperty<Character> {

	//================================================================================
	// Constructors
	//================================================================================
	public CharProperty() {
	}

	public CharProperty(Character initialValue) {
		super(initialValue);
	}

	public CharProperty(Object bean, String name) {
		super(bean, name);
	}

	public CharProperty(Object bean, String name, Character initialValue) {
		super(bean, name, initialValue);
	}
}
