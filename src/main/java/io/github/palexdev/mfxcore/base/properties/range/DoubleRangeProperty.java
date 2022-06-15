package io.github.palexdev.mfxcore.base.properties.range;

import io.github.palexdev.mfxcore.base.beans.range.DoubleRange;
import io.github.palexdev.mfxcore.base.beans.range.NumberRange;
import io.github.palexdev.mfxcore.base.properties.base.NumberRangeProperty;

/**
 * Implementation of {@link NumberRangeProperty} for {@code Double} ranges.
 */
public class DoubleRangeProperty extends NumberRangeProperty<Double> {

	//================================================================================
	// Constructors
	//================================================================================
	public DoubleRangeProperty() {
	}

	public DoubleRangeProperty(NumberRange<Double> initialValue) {
		super(initialValue);
	}

	public DoubleRangeProperty(Object bean, String name) {
		super(bean, name);
	}

	public DoubleRangeProperty(Object bean, String name, NumberRange<Double> initialValue) {
		super(bean, name, initialValue);
	}

	//================================================================================
	// Overridden Methods
	//================================================================================
	@Override
	public void setRange(Double value) {
		set(DoubleRange.of(value));
	}

	@Override
	public void setRange(Double min, Double max) {
		set(DoubleRange.of(min, max));
	}
}
