package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

public class Enemy implements ICharacter {

	private Coord localCoord;
	private Texture texture;
	private int width,heigth;

	public Enemy(Texture text, Coord coord) {
		localCoord = coord;
		texture = text;
		width = text.getWidth();
		heigth = text.getHeight();
	}

	@Override
	public void setCoord(Coord newCoord) {
		localCoord = newCoord;
	}

	@Override
	public Coord getCoord() {
		return localCoord;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

	@Override
	public double getX() {
		return localCoord.x;
	}

	@Override
	public double getY() {
		return localCoord.y;
	}

	@Override
	public double getAngle() {
		return -(90+localCoord.angle);
	}

	@Override
	public void setX(double x) {
		localCoord.x = x;
	}

	@Override
	public void setY(double y) {
		localCoord.y = y;
	}

	@Override
	public void setAngle(double ang) {
		localCoord.angle = ang;
	}

	@Override
	public void setAngle(double destX, double destY) {
		localCoord.angle = com.ponycornteam.tools.Trigo.angleCalc(localCoord.x+width/2,
				localCoord.y+heigth/2, destX, destY);
	}

	@Override
	public void setTexture(Texture text) {
		texture = text;
		width = text.getWidth();
		heigth = text.getHeight();
	}
}
