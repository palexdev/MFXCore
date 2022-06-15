package io.github.palexdev.mfxcore.base.beans;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Class used in various controls as a workaround for showing a node two or more times on the scene graph.
 * <p>
 * Makes a screenshot of a node with transparent background.
 * Then {@link #getGraphic()} should be used to get an {@code ImageView} node which contains the screenshot.
 * <p></p>
 * A little side note: since it is a screenshot the image may appear a little blurry compared to the real
 * graphic, however it should be acceptable and I believe this is still better than having no graphic at all.
 */
public class SnapshotWrapper {
	private final WritableImage snapshot;

	public SnapshotWrapper(Node node) {
		SnapshotParameters snapshotParameters = new SnapshotParameters();
		snapshotParameters.setFill(Color.TRANSPARENT);
		snapshotParameters.setDepthBuffer(true);
		snapshot = node.snapshot(snapshotParameters, null);
	}

	public Node getGraphic() {
		return new ImageView(snapshot);
	}
}
