package io.github.palexdev.mfxcore.base.beans;

import java.util.function.BiPredicate;

/**
 * A simple immutable bean that wraps a {@link BiPredicate} and a String that represents
 * the name for the predicate.
 * <p></p>
 * Note that the {@link #toString()} method has been overridden to return the given name.
 *
 * @param <T> the type of the first predicate's argument
 * @param <U> the type of the second predicate's argument
 */
public class BiPredicateBean<T, U> {
	//================================================================================
	// Properties
	//================================================================================
	private final String name;
	private final BiPredicate<T, U> predicate;

	//================================================================================
	// Constructors
	//================================================================================
	public BiPredicateBean(String name, BiPredicate<T, U> predicate) {
		this.name = name;
		this.predicate = predicate;
	}

	//================================================================================
	// Getters
	//================================================================================
	public String getName() {
		return name;
	}

	public BiPredicate<T, U> getPredicate() {
		return predicate;
	}

	@Override
	public String toString() {
		return name;
	}
}
