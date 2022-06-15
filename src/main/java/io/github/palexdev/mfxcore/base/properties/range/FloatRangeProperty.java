package io.github.palexdev.mfxcore.base.properties.range;

import io.github.palexdev.mfxcore.base.beans.range.FloatRange;
import io.github.palexdev.mfxcore.base.beans.range.NumberRange;
import io.github.palexdev.mfxcore.base.properties.base.NumberRangeProperty;

/**
 * Implementation of {@link NumberRangeProperty} for {@code Float} ranges.
 */
public class FloatRangeProperty extends NumberRangeProperty<Float> {

	//================================================================================
	// Constructors
	//================================================================================
	public FloatRangeProperty() {
	}

	public FloatRangeProperty(NumberRange<Float> initialValue) {
		super(initialValue);
	}

	public FloatRangeProperty(Object bean, String name) {
		super(bean, name);
	}

	public FloatRangeProperty(Object bean, String name, NumberRange<Float> initialValue) {
		super(bean, name, initialValue);
	}

	//================================================================================
	// Overridden Methods
	//================================================================================
	@Override
	public void setRange(Float value) {
		set(FloatRange.of(value));
	}

	@Override
	public void setRange(Float min, Float max) {
		set(FloatRange.of(min, max));
	}
}
