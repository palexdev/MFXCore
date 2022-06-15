package io.github.palexdev.mfxcore.base.properties.base;

import io.github.palexdev.mfxcore.observables.When;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ObservableValue;

public interface SynchronizedProperty<T> extends Property<T> {

	/**
	 * Sets this property's state to "waiting" then uses {@link When#onChanged(ObservableValue)}
	 * to "awake" the property when the given observable changes.
	 * <p></p>
	 * Just like JavaFX properties if the new value is the same as the current value the method returns and does nothing.
	 *
	 * @param value      the new value of the property
	 * @param observable the observable to wait for
	 * @throws IllegalArgumentException if the given observable is the property itself or if the passed observable
	 *                                  is another SynchronizedProperty, and it is already waiting for some other observable
	 * @throws IllegalStateException    if this property is already waiting for another observable
	 */
	void setAndWait(T value, ObservableValue<?> observable);

	/**
	 * @return whether this property is in waiting state
	 */
	boolean isWaiting();

	/**
	 * @return the waiting state property as a read only property
	 */
	ReadOnlyBooleanProperty waiting();

	/**
	 * Awakes the property by setting {@link #waiting()} to false.
	 * <p></p>
	 * This method should never be invoked by the user, the awakening is automatically
	 * managed by the property. If for some reason the property stays in waiting state you
	 * are probably doing something wrong.
	 */
	void awake();

	/**
	 * Helper class to avoid code duplication.
	 */
	class Helper {

		/**
		 * Check some parameters before proceeding with the set and wait method
		 *
		 * @param value      the new value of the property
		 * @param observable the observable to wait for
		 * @return whether the check failed
		 * @throws IllegalArgumentException if the given observable is the property itself or if the passed observable
		 *                                  is another SynchronizedProperty, and it is already waiting for some other observable
		 * @throws IllegalStateException    if the property is bound unidirectionally, or
		 *                                  if this property is already waiting for another observable
		 */
		public static <T> boolean check(SynchronizedProperty<T> property, T value, ObservableValue<?> observable) {
			if (observable == property) {
				throw new IllegalArgumentException("The passed property cannot be the same as this!" +
						" Proceeding with this method would lead to a deadlock.");
			}
			if (property.isBound()) {
				throw new IllegalStateException("A bound value cannot be set!");
			}
			if (property.isWaiting()) {
				throw new IllegalStateException("The property is already waiting for some other observable!");
			}
			if (observable instanceof SynchronizedProperty) {
				SynchronizedProperty<?> synchronizedProperty = (SynchronizedProperty<?>) observable;
				if (synchronizedProperty.isWaiting()) {
					throw new IllegalArgumentException("The passed property is already waiting for some other property!" +
							" Proceeding with this method would lead to a deadlock.");
				}
			}
			return property.getValue() == null || !property.getValue().equals(value);
		}
	}
}
