package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

abstract class Character implements ICharacter{

	public Coord localCoord;
	public Texture texture;
	protected int width,heigth;

	public void setCoord(Coord newCoord) {
		localCoord = newCoord;
	}

	public Coord getCoord() {
		return localCoord;
	}

	public Texture getTexture() {
		return texture;
	}
	public double getX(){
		return localCoord.x;
	}
	
	public double getY(){
		return localCoord.y;
	}
	
	public double getAngle() {
		return -(localCoord.angle);
	}

	public void setX(double x) {
		localCoord.x = x;
	}

	public void setY(double y) {
		localCoord.y = y;
	}

	public void setAngle(double ang) {
		localCoord.angle = ang;
	}

	public void setAngle(double destX, double destY) {
		localCoord.angle = com.ponycornteam.tools.Trigo.angleCalc(localCoord.x+width/2,
				localCoord.y+heigth/2, destX, destY);
	}

	public void setTexture(Texture text) {
		texture = text;
		width = text.getWidth();
		heigth = text.getHeight();
	}
}
