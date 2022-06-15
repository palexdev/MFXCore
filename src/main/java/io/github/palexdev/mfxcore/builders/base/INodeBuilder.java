package io.github.palexdev.mfxcore.builders.base;

import javafx.scene.Node;

public interface INodeBuilder<N extends Node> {
	N getNode();
}
