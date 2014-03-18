package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

public interface ICharacter {
	public void setCoord(Coord newCoord);
	public Coord getCoord();
	public Texture getTexture();
	public void setTexture(Texture text);
	public double getX();
	public double getY();
	public double getAngle();
	public void setX(double x);
	public void setY(double y);
	public void setAngle(double ang);
	public void setAngle(double destX, double destY);
}
