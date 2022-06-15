package io.github.palexdev.mfxcore.utils.resize.base;

import javafx.scene.Node;

@FunctionalInterface
public interface DragResizeHandler<T extends Node> {
	void onResize(T node, double x, double y, double w, double h);
}