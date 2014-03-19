package com.ponycornteam.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ponycornteam.tools.Coord;

public class Projectile implements IDrawable {
	public Coord localCoord;
	public Sprite texture;
	public double speed;
	protected int width, heigth;
	public Character owner = null;
	private float oldTime = 0;

	private void avancer(float deltaTime) {
		if (speed > 0) {
			localCoord.x += Math.cos(-localCoord.angle / 180 * Math.PI) * speed
					* deltaTime;
			localCoord.y += Math.sin(-localCoord.angle / 180 * Math.PI) * speed
					* deltaTime;
			speed -= 100 * deltaTime;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
		avancer(Gdx.graphics.getDeltaTime());
		texture.setPosition((float) localCoord.x, (float) localCoord.y);
		texture.setRotation((float) -localCoord.angle);
		texture.draw(batch);
	}
}
