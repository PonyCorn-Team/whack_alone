package com.ponycornteam.core.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ponycornteam.tools.Coord;
import com.ponycornteam.tools.Coord.direction;

public class Projectile implements IDrawable, IColidable {
	public Coord localCoord;
	public Sprite texture;
	public double speed;
	protected int width, heigth;
	public ICharacter owner = null;
	public Sound shootSound;
	public Sound bounceSound;
	public Boolean isDestroy = false;

	private void avancer(float deltaTime) {
		if (speed > 0) {
			localCoord.x += Math.cos(-localCoord.angle / 180 * Math.PI) * speed
					* deltaTime;
			localCoord.y += Math.sin(-localCoord.angle / 180 * Math.PI) * speed
					* deltaTime;
			speed -= 75 * deltaTime;
		}
	}
	
	public void draw(SpriteBatch batch, float stateTime) {
		avancer(Gdx.graphics.getDeltaTime());
		texture.setPosition((float) localCoord.x, (float) localCoord.y);
		texture.setRotation((float) -localCoord.angle);
		texture.draw(batch);
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
	public Rectangle getRectangle() {
		return new Rectangle((float)localCoord.x,(float)localCoord.y,(float)texture.getWidth(),(float)texture.getHeight());
	}

	@Override
	public void colisionObject(direction dir) {
		speed /=2;
		localCoord.angle = ((dir == direction.top||dir == direction.bot)?360:180)-localCoord.angle;
	}

	@Override
	public void colisionProjectile(Projectile projectile) {
		// TODO Auto-generated method stub
		
	}
}