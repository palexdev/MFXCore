package io.github.palexdev.mfxcore.utils.resize.shapes;

import io.github.palexdev.mfxcore.enums.Zone;
import io.github.palexdev.mfxcore.utils.resize.base.AbstractDragResizer;
import io.github.palexdev.mfxcore.utils.resize.base.DragResizeHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.function.Function;

public class RectangleDragResizer extends AbstractDragResizer<Rectangle> {
	private Function<Rectangle, Double> minWidthFunction = r -> 0.0;
	private Function<Rectangle, Double> minHeightFunction = r -> 0.0;

	public RectangleDragResizer(Rectangle node) {
		this(node, (r, x, y, w, h) -> {
			r.setLayoutX(x);
			r.setLayoutY(y);
			r.setWidth(w);
			r.setHeight(h);
		});
	}

	public RectangleDragResizer(Rectangle node, DragResizeHandler<Rectangle> resizeHandler) {
		super(node, resizeHandler);
	}

	@Override
	protected void handleDragged(MouseEvent event) {
		if (draggedZone == Zone.NONE) return;

		double currX = event.getSceneX();
		double currY = event.getSceneY();
		double deltaX = currX - clickedX;
		double deltaY = currY - clickedY;
		double widthDelta;
		double heightDelta;
		double newX = nodeX;
		double newY = nodeY;
		double newW = nodeW;
		double newH = nodeH;

		if (Zone.isRight(draggedZone)) {
			widthDelta = deltaX;
			newW += widthDelta;
		} else if (Zone.isLeft(draggedZone)) {
			widthDelta = -deltaX;
			newX = nodeX - widthDelta;
			newW += widthDelta;
		}

		if (Zone.isBottom(draggedZone)) {
			heightDelta = deltaY;
			newH += heightDelta;
		} else if (Zone.isTop(draggedZone)) {
			heightDelta = -deltaY;
			newY = nodeY - heightDelta;
			newH += heightDelta;
		}

		double minW = minWidthFunction.apply(node);
		double minH = minWidthFunction.apply(node);

		if (newW < minW) {
			if (Zone.isLeft(draggedZone)) {
				newX = newX - minW + newW;
			}
			newW = minW;
		}

		if (newH < minH) {
			if (Zone.isTop(draggedZone)) {
				newY = newY + newH - minH;
			}
			newH = minH;
		}

		resizeHandler.onResize(node, newX, newY, newW, newH);
	}

	public Function<Rectangle, Double> getMinWidthFunction() {
		return minWidthFunction;
	}

	public void setMinWidthFunction(Function<Rectangle, Double> minWidthFunction) {
		this.minWidthFunction = minWidthFunction;
	}

	public Function<Rectangle, Double> getMinHeightFunction() {
		return minHeightFunction;
	}

	public void setMinHeightFunction(Function<Rectangle, Double> minHeightFunction) {
		this.minHeightFunction = minHeightFunction;
	}
}
