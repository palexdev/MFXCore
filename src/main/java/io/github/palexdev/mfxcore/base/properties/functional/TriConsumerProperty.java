package io.github.palexdev.mfxcore.base.properties.functional;

import io.github.palexdev.mfxcore.base.TriConsumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Simply an {@link ObjectProperty} that wraps a {@link TriConsumer}.
 *
 * @param <T> the consumer's first argument
 * @param <U> the consumer's second argument
 * @param <R> the consumer's third argument
 */
public class TriConsumerProperty<T, U, R> extends SimpleObjectProperty<TriConsumer<T, U, R>> {

	//================================================================================
	// Constructors
	//================================================================================
	public TriConsumerProperty() {
	}

	public TriConsumerProperty(TriConsumer<T, U, R> initialValue) {
		super(initialValue);
	}

	public TriConsumerProperty(Object bean, String name) {
		super(bean, name);
	}

	public TriConsumerProperty(Object bean, String name, TriConsumer<T, U, R> initialValue) {
		super(bean, name, initialValue);
	}
}
