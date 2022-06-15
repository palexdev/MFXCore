package io.github.palexdev.mfxcore.base.beans;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;

/**
 * Simple bean that keeps track of two coordinates, x and y.
 * <p>
 * Both are JavaFX properties to allow dynamic uses.
 */
public class PositionBean {
	//================================================================================
	// Properties
	//================================================================================
	private final DoubleProperty x = new SimpleDoubleProperty(0);
	private final DoubleProperty y = new SimpleDoubleProperty(0);

	//================================================================================
	// Constructors
	//================================================================================
	public PositionBean() {
	}

	public PositionBean(double x, double y) {
		setX(x);
		setY(y);
	}

	//================================================================================
	// Static Methods
	//================================================================================
	public static PositionBean of(double x, double y) {
		return new PositionBean(x, y);
	}

	//================================================================================
	// Overridden Methods
	//================================================================================
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PositionBean that = (PositionBean) o;
		return getX() == (that.getX()) && getY() == (that.getY());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

	@Override
	public String toString() {
		return "X|Y (" + getX() + "; " + getY() + ")";
	}

	//================================================================================
	// Methods
	//================================================================================
	public double getX() {
		return x.get();
	}

	/**
	 * The x coordinate property.
	 */
	public DoubleProperty xProperty() {
		return x;
	}

	public void setX(double xPosition) {
		this.x.set(xPosition);
	}

	public double getY() {
		return y.get();
	}

	/**
	 * The y coordinate property
	 */
	public DoubleProperty yProperty() {
		return y;
	}

	public void setY(double yPosition) {
		this.y.set(yPosition);
	}
}

