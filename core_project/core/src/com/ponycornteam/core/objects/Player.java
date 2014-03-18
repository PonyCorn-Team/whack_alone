package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

public class Player implements ICharacter {

	private Coord localCoord;
	private Texture texture;
	
	public Player(Texture text, Coord coord)
	{
		localCoord = coord;
		texture = text; 
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
}
