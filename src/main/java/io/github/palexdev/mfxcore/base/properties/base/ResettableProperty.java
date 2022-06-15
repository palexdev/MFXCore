package io.github.palexdev.mfxcore.base.properties.base;

import javafx.beans.property.Property;

/**
 * Base interface for all resettable properties.
 *
 * @param <T> the type of the wrapped value
 */
public interface ResettableProperty<T> extends Property<T> {

	/**
	 * Sets the property's value to the default value.
	 */
	default void reset() {
		setValue(getDefaultValue());
	}

	boolean isFireChangeOnReset();

	/**
	 * Specifies if the property should fire a change event when it is reset or not.
	 */
	void setFireChangeOnReset(boolean fireChangeOnReset);

	/**
	 * @return true if the property has been reset.
	 */
	boolean hasBeenReset();

	/**
	 * @return the property's default value
	 */
	T getDefaultValue();

	/**
	 * Sets the property's default value to the given value.
	 */
	void setDefaultValue(T defaultValue);
}
