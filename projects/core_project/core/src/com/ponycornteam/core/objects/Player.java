package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

public class Player extends Character {
	public Player(Texture text, Coord coord)
	{
		localCoord = coord;
		texture = text; 
		width = text.getWidth();
		heigth = text.getHeight();
	}
}
