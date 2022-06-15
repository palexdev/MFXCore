module MFXCore {
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive java.desktop;

	requires transitive MFXLocalization;

	// Animations
	exports io.github.palexdev.mfxcore.animations;

	// Base
	exports io.github.palexdev.mfxcore.base;
	exports io.github.palexdev.mfxcore.base.beans;
	exports io.github.palexdev.mfxcore.base.beans.range;
	exports io.github.palexdev.mfxcore.base.bindings;
	exports io.github.palexdev.mfxcore.base.bindings.base;
	exports io.github.palexdev.mfxcore.base.properties;
	exports io.github.palexdev.mfxcore.base.properties.base;
	exports io.github.palexdev.mfxcore.base.properties.functional;
	exports io.github.palexdev.mfxcore.base.properties.range;
	exports io.github.palexdev.mfxcore.base.properties.resettable;
	exports io.github.palexdev.mfxcore.base.properties.styleable;
	exports io.github.palexdev.mfxcore.base.properties.synced;

	// Builders
	exports io.github.palexdev.mfxcore.builders;
	exports io.github.palexdev.mfxcore.builders.base;
	exports io.github.palexdev.mfxcore.builders.bindings;
	exports io.github.palexdev.mfxcore.builders.nodes;

	// Collections
	exports io.github.palexdev.mfxcore.collections;

	// Enums
	exports io.github.palexdev.mfxcore.enums;

	// Filter
	exports io.github.palexdev.mfxcore.filter;
	exports io.github.palexdev.mfxcore.filter.base;

	// Observables
	exports io.github.palexdev.mfxcore.observables;

	// Utils
	exports io.github.palexdev.mfxcore.utils;
	exports io.github.palexdev.mfxcore.utils.converters;
	exports io.github.palexdev.mfxcore.utils.fx;
	exports io.github.palexdev.mfxcore.utils.loader;
	exports io.github.palexdev.mfxcore.utils.resize;
	exports io.github.palexdev.mfxcore.utils.resize.base;
	exports io.github.palexdev.mfxcore.utils.resize.shapes;

	// Validations
	exports io.github.palexdev.mfxcore.validation;
}