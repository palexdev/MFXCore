package io.github.palexdev.mfxcore.base.properties.range;

import io.github.palexdev.mfxcore.base.beans.range.IntegerRange;
import io.github.palexdev.mfxcore.base.beans.range.NumberRange;
import io.github.palexdev.mfxcore.base.properties.base.NumberRangeProperty;

/**
 * Implementation of {@link NumberRangeProperty} for {@code Integer} ranges.
 */
public class IntegerRangeProperty extends NumberRangeProperty<Integer> {

	//================================================================================
	// Constructors
	//================================================================================
	public IntegerRangeProperty() {
	}

	public IntegerRangeProperty(NumberRange<Integer> initialValue) {
		super(initialValue);
	}

	public IntegerRangeProperty(Object bean, String name) {
		super(bean, name);
	}

	public IntegerRangeProperty(Object bean, String name, NumberRange<Integer> initialValue) {
		super(bean, name, initialValue);
	}

	//================================================================================
	// Overridden Methods
	//================================================================================
	@Override
	public void setRange(Integer value) {
		set(IntegerRange.of(value));
	}

	@Override
	public void setRange(Integer min, Integer max) {
		set(IntegerRange.of(min, max));
	}
}
