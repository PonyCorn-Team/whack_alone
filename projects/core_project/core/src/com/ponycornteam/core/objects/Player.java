package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ponycornteam.tools.Coord;

public class Player extends Character {
	float oldTime = 0;

	public Player(Texture standing, Coord coord) {
		localCoord = coord;
		this.standing = new Sprite(standing);
		width = standing.getWidth();
		heigth = standing.getHeight();
	}

	public void setMoving(Boolean move) {
		this.move = move;
	}

	public void draw(SpriteBatch batch, float stateTime, Boolean move) {
		this.move = move;
		draw(batch, stateTime - oldTime);
	}
	public void setText(String txt)
	{
		saying = txt;
	}
}
