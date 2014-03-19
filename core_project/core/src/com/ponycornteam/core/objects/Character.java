package com.ponycornteam.core.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ponycornteam.tools.Coord;

abstract class Character implements ICharacter, IDrawable {

	public Coord localCoord;
	public boolean dead = false, move = false;
	protected int width, heigth;
	protected Sprite standing, dying;
	protected Animation moving;
	protected String saying = "";

	public Array<Sound>  sAie;
	public Array<Sound>  sPaf;
    
	public Sound deadSound;
	
	public void setCoord(Coord newCoord) {
		localCoord = newCoord;
	}

	public Coord getCoord() {
		return localCoord;
	}

	public double getX() {
		return localCoord.x;
	}

	public double getY() {
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
		localCoord.angle = com.ponycornteam.tools.Trigo.angleCalc(localCoord.x
				+ width / 2, localCoord.y + heigth / 2, destX, destY);
	}

	public void setStanding(Texture text) {
		standing = new Sprite(text);
		width = text.getWidth();
		heigth = text.getHeight();
	}

	public void setMoving(Animation text) {
		moving = text;
	}

	public void setDying(Texture text) {
		dying = new Sprite(text);
	}

	public void draw(SpriteBatch batch, float stateTime) {
		Sprite sp;
		if (dead && dying != null)
			sp = dying;
		else if (move && moving != null)
			sp = new Sprite(moving.getKeyFrame(stateTime, true));
		else
			sp = standing;
		sp.setPosition((float) localCoord.x, (float) localCoord.y);
		sp.setRotation((float) -localCoord.angle);
		sp.draw(batch);
		if (!saying.isEmpty())
			drawText(batch, saying);
	}

	public double getWidth() {
		return width;
	}

	public double getHeigh() {
		return heigth;
	}
	
	protected void drawText(SpriteBatch batch, String txt)
	{

        BitmapFont font = new BitmapFont(); 
        font.setColor(Color.WHITE);
        font.setScale(1f);
		font.draw(batch, "hello", (float)(localCoord.x + width +10), (float)(localCoord.y + heigth+10));
	}
}
