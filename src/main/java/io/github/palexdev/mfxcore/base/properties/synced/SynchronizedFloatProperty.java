/*
 * Copyright (C) 2022 Parisi Alessandro
 * This file is part of MFXCore (https://github.com/palexdev/MFXCore).
 *
 * MFXCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MFXCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MFXCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.mfxcore.base.properties.synced;

import io.github.palexdev.mfxcore.base.bindings.BiBindingManager;
import io.github.palexdev.mfxcore.base.bindings.BindingManager;
import io.github.palexdev.mfxcore.base.properties.base.SynchronizedProperty;
import io.github.palexdev.mfxcore.observables.When;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableValue;

/**
 * Implementation of {@link SynchronizedProperty} for float values.
 */
public class SynchronizedFloatProperty extends SimpleFloatProperty implements SynchronizedProperty<Number> {
	//================================================================================
	// Properties
	//================================================================================
	private final ReadOnlyBooleanWrapper waiting = new ReadOnlyBooleanWrapper();

	//================================================================================
	// Constructors
	//================================================================================
	public SynchronizedFloatProperty() {
		initialize();
	}

	public SynchronizedFloatProperty(float initialValue) {
		super(initialValue);
		initialize();
	}

	public SynchronizedFloatProperty(Object bean, String name) {
		super(bean, name);
		initialize();
	}

	public SynchronizedFloatProperty(Object bean, String name, float initialValue) {
		super(bean, name, initialValue);
		initialize();
	}

	//================================================================================
	// Methods
	//================================================================================

	/**
	 * Adds a listener to the property by calling {@link When#onChanged(ObservableValue)}
	 * to call {@link #fireValueChangedEvent()} when the property is awakened, {@link #awake()}.
	 */
	private void initialize() {
		When.onChanged(waiting)
				.then((oldValue, newValue) -> {
					if (!newValue) fireValueChangedEvent();
				})
				.listen();
	}

	//================================================================================
	// Implemented/Overridden Methods
	//================================================================================

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAndWait(Number value, ObservableValue<?> observable) {
		if (!Helper.check(this, value, observable)) return;

		waiting.set(true);
		When.onChanged(observable)
				.then((oldValue, newValue) -> awake())
				.oneShot()
				.listen();
		set(value.floatValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWaiting() {
		return waiting.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReadOnlyBooleanProperty waiting() {
		return waiting.getReadOnlyProperty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void awake() {
		waiting.set(false);
	}

	/**
	 * {@inheritDoc}
	 * <p></p>
	 * Overridden to not fire a change event if {@link #waiting()} is true.
	 */
	@Override
	protected void fireValueChangedEvent() {
		if (isWaiting()) return;
		super.fireValueChangedEvent();
	}

	//================================================================================
	// Binding
	//================================================================================

	/**
	 * Creates a unidirectional bindings with the given observable.
	 * <p>
	 * The binding is created using the new {@link BindingManager} mechanism.
	 * <p></p>
	 * If the property is already bound it is automatically unbound before bindings to the new observable.
	 *
	 * @throws IllegalArgumentException if the given observable is the property itself
	 * @see BindingManager
	 */
	@Override
	public void bind(ObservableValue<? extends Number> source) {
		if (this == source) {
			throw new IllegalArgumentException("Cannot bind to itself!");
		}

		if (isBound()) unbind();
		BindingManager.instance().bind(this).to(source).create();
	}

	/**
	 * Creates a bidirectional bindings between this property and the given property.
	 * <p>
	 * The binding is created using the new {@link BiBindingManager} mechanism.
	 * <p></p>
	 * If the property is already bound unidirectionally it is automatically unbound.
	 * <p>
	 * If the property is already bound bidirectionally it won't be automatically unbound, just like JavaFX,
	 * this way you can have multiple bidirectional bindings
	 *
	 * @throws IllegalArgumentException if the given observable is the property itself
	 * @see BiBindingManager
	 */
	@Override
	public void bindBidirectional(Property<Number> other) {
		if (this == other) {
			throw new IllegalArgumentException("Cannot bind to itself!");
		}

		if (isBound()) unbind();
		BiBindingManager.instance().bindBidirectional(this).to(other).create();
	}

	/**
	 * Overridden to call {@link BindingManager#unbind(ObservableValue)}.
	 */
	@Override
	public void unbind() {
		BindingManager.instance().unbind(this);
	}

	/**
	 * Overridden to call {@link BiBindingManager#unbind(ObservableValue, ObservableValue)}.
	 */
	@Override
	public void unbindBidirectional(Property<Number> other) {
		BiBindingManager.instance().unbind(this, other);
	}

	/**
	 * Delegate method for {@link BiBindingManager#disposeFor(ObservableValue)}.
	 */
	public void clearBidirectional() {
		BiBindingManager.instance().disposeFor(this);
	}

	/**
	 * Overridden to check the {@link BindingManager#isBound(ObservableValue)} and {@link BindingManager#isIgnoreBinding(ObservableValue)}.
	 *
	 * @return true only if `BindingManager.isBound()` is true and `isIgnoreBound()` is false
	 */
	@Override
	public boolean isBound() {
		return BindingManager.instance().isBound(this) && !BindingManager.instance().isIgnoreBinding(this);
	}
}
