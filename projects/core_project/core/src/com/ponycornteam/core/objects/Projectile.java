package com.ponycornteam.core.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ponycornteam.tools.Coord;

public class Projectile implements IDrawable {
	public Coord localCoord;
	public Sprite texture;
	public double speed;
	protected int width, heigth;
	public Character owner = null;
	public Sound shootSound;
	public Sound bounceSound;

	private void avancer(float deltaTime) {
		if (speed > 0) {
			localCoord.x += Math.cos(localCoord.angle / 180 * Math.PI) * speed
					* deltaTime;
			localCoord.y += Math.sin(localCoord.angle / 180 * Math.PI) * speed
					* deltaTime;
			speed -= 9.81 * deltaTime;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
		avancer(stateTime);
		texture.setPosition((float) localCoord.x, (float) localCoord.y);
		texture.setRotation((float) localCoord.angle);
		texture.draw(batch);
	}
}
