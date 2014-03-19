package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ponycornteam.tools.Coord;

public class Enemy extends Character {
	public Enemy(Texture stand, Coord coord) {
		localCoord = coord;
		this.standing = new Sprite(stand);
		width = stand.getWidth();
		heigth = stand.getHeight();
	}
}
