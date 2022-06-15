package io.github.palexdev.mfxcore.base.properties.range;

import io.github.palexdev.mfxcore.base.beans.range.LongRange;
import io.github.palexdev.mfxcore.base.beans.range.NumberRange;
import io.github.palexdev.mfxcore.base.properties.base.NumberRangeProperty;

/**
 * Implementation of {@link NumberRangeProperty} for {@code Long} ranges.
 */
public class LongRangeProperty extends NumberRangeProperty<Long> {

	//================================================================================
	// Constructors
	//================================================================================
	public LongRangeProperty() {
	}

	public LongRangeProperty(NumberRange<Long> initialValue) {
		super(initialValue);
	}

	public LongRangeProperty(Object bean, String name) {
		super(bean, name);
	}

	public LongRangeProperty(Object bean, String name, NumberRange<Long> initialValue) {
		super(bean, name, initialValue);
	}

	//================================================================================
	// Overridden Methods
	//================================================================================
	@Override
	public void setRange(Long value) {
		set(LongRange.of(value));
	}

	@Override
	public void setRange(Long min, Long max) {
		set(LongRange.of(min, max));
	}
}
