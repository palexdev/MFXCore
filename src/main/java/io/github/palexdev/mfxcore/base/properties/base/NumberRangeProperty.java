package io.github.palexdev.mfxcore.base.properties.base;

import io.github.palexdev.mfxcore.base.beans.range.NumberRange;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Simply an {@link ObjectProperty} that wraps a {@link NumberRange}.
 *
 * @param <N> the range's number type
 */
public abstract class NumberRangeProperty<N extends Number & Comparable<N>> extends SimpleObjectProperty<NumberRange<N>> {

	//================================================================================
	// Constructors
	//================================================================================
	public NumberRangeProperty() {
	}

	public NumberRangeProperty(NumberRange<N> initialValue) {
		super(initialValue);
	}

	public NumberRangeProperty(Object bean, String name) {
		super(bean, name);
	}

	public NumberRangeProperty(Object bean, String name, NumberRange<N> initialValue) {
		super(bean, name, initialValue);
	}

	//================================================================================
	// Abstract Methods
	//================================================================================

	/**
	 * Convenience method to set a range with both min and max equal.
	 */
	public abstract void setRange(N value);

	/**
	 * Convenience method to set a range with the given min and max values.
	 */
	public abstract void setRange(N min, N max);

	//================================================================================
	// Methods
	//================================================================================

	/**
	 * Convenience method to get the range's lower bound.
	 * Null if the range is null.
	 */
	public N getMin() {
		return get() == null ? null : get().getMin();
	}

	/**
	 * Convenience method to get the range's upper bound.
	 * Null if the range is null.
	 */
	public N getMax() {
		return get() == null ? null : get().getMin();
	}

	//================================================================================
	// Overridden Methods
	//================================================================================

	/**
	 * Overridden to check equality between ranges and return in case ranges are the same.
	 */
	@Override
	public void set(NumberRange<N> newValue) {
		NumberRange<N> oldValue = get();
		if (newValue.equals(oldValue)) return;
		super.set(newValue);
	}
}
