package com.ponycornteam.core.objects;

import com.badlogic.gdx.math.Rectangle;
import com.ponycornteam.tools.Coord.direction;

public interface IColidable {
	public void setX(double x);

	public void setY(double y);

	public Rectangle getRectangle();

	public void colisionObject(direction dir);
}
