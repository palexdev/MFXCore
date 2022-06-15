package io.github.palexdev.mfxcore.base.beans.range;

import java.util.Objects;

/**
 * Base class to represent a range of numbers between a min and a max.
 *
 * @param <N> The type of Number to represent (must also be a {@link Comparable})
 */
public abstract class NumberRange<N extends Number & Comparable<N>> {
	//================================================================================
	// Properties
	//================================================================================
	private final N min;
	private final N max;

	//================================================================================
	// Constructors
	//================================================================================
	public NumberRange(N min, N max) {
		if (min == null || max == null || min.compareTo(max) > 0) {
			throw new IllegalArgumentException("Invalid range for values: Min[" + getMin() + "], Max[" + getMax() + "]");
		}

		this.min = min;
		this.max = max;
	}

	//================================================================================
	// Getters
	//================================================================================

	/**
	 * @return the lower bound
	 */
	public N getMin() {
		return min;
	}

	/**
	 * @return the upper bound
	 */
	public N getMax() {
		return max;
	}

	//================================================================================
	// Overridden Methods
	//================================================================================
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NumberRange<?> that = (NumberRange<?>) o;
		return getMin().equals(that.getMin()) && getMax().equals(that.getMax());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMin(), getMax());
	}

	@Override
	public String toString() {
		return "Min[" + getMin() + "], Max[" + getMax() + "]";
	}
}
