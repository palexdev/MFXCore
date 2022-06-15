package io.github.palexdev.mfxcore.base.properties;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;

public class EventHandlerProperty<T extends Event> extends SimpleObjectProperty<EventHandler<T>> {

	//================================================================================
	// Constructors
	//================================================================================
	public EventHandlerProperty(EventHandler<T> initialValue) {
		super(initialValue);
	}

	public EventHandlerProperty(Object bean, String name) {
		super(bean, name);
	}

	public EventHandlerProperty(Object bean, String name, EventHandler<T> initialValue) {
		super(bean, name, initialValue);
	}
}
