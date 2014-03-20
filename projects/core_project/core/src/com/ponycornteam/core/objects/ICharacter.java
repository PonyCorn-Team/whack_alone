package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.ponycornteam.tools.Coord;

public interface ICharacter extends IDrawable, IColidable, IColidableCharacter {
	public void setCoord(Coord newCoord);

	public Coord getCoord();

	public void setMoving(Animation text);

	public void setStanding(Texture text);

	public void setDying(Texture text);

	public double getX();

	public double getY();

	public double getWidth();

	public double getHeigh();

	public double getAngle();

	public void setX(double x);

	public void setY(double y);

	public void setAngle(double ang);

	public void setAngle(double destX, double destY);
}
