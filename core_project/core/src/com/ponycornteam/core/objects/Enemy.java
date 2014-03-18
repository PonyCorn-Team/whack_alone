package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

public class Enemy extends Character {
	public Enemy(Texture text, Coord coord) {
		localCoord = coord;
		texture = text;
		width = text.getWidth();
		heigth = text.getHeight();
	}
}
