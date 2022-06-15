package io.github.palexdev.mfxcore.builders.base;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class BindingBuilder<T, B extends Binding<? super T>> {
	protected List<Observable> sources = new ArrayList<>();
	protected Callable<T> mapper;

	protected abstract B create();

	public BindingBuilder<T, B> addSources(Observable... sources) {
		Collections.addAll(this.sources, sources);
		return this;
	}

	public BindingBuilder<T, B> setSources(Observable... sources) {
		this.sources.clear();
		addSources(sources);
		return this;
	}

	public BindingBuilder<T, B> setMapper(Callable<T> mapper) {
		this.mapper = mapper;
		return this;
	}

	public List<Observable> getSources() {
		return sources;
	}

	public Observable[] getSourcesArray() {
		return sources.toArray(Observable[]::new);
	}

	public Callable<T> getMapper() {
		return mapper;
	}

	public B get() {
		return create();
	}
}
