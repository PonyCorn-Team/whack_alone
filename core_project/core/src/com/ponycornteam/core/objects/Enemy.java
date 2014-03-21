package com.ponycornteam.core.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ponycornteam.core.GameScreen;
import com.ponycornteam.core.objects.Projectile.Ammo;
import com.ponycornteam.tools.Coord;
import com.ponycornteam.tools.Coord.direction;

public class Enemy extends Character {
	private static Random random = new Random();
	private static double timeToReload = 5, recoil = 0;
	private Ammo ammotype;
	private Sprite texture;
	public Enemy(Texture stand, Coord coord, Ammo ammotype, Sprite texture) {
		localCoord = coord;
		this.standing = new Sprite(stand);
		width = stand.getWidth();
		heigth = stand.getHeight();
		this.ammotype = ammotype;
		this.texture = texture;
		if (Ammo.bullet == ammotype) {
			Projectile pro;
			for (int i = 0; i < 5; i++) {
				pro = new Projectile();
				pro.ammoType = ammotype;
				pro.texture = texture;
				ammo.add(pro);
			}
		}
	}

	@Override
	public void colisionProjectile(Projectile projectile) {
		if (projectile.owner != this && projectile.owner != null && projectile.speed > 0) {
			saying = edead.random();
			saycolor = Color.RED;
			sayingCount = 10.0;
			dead = true;
			projectile.owner.setText(pkill.random(), Color.ORANGE, 2.0);
			if (deadSound != null)
				deadSound.play();
		}
	}

	public void enemyIA(ICharacter player) {
		if (dead)
			return;
		double d = random.nextDouble() * 50 + 75;
		localCoord.angle+=random.nextDouble()*4-2;
		localCoord.x += Math.cos(-localCoord.angle / 180 * Math.PI) * d * Gdx.graphics.getDeltaTime();
		localCoord.y += Math.sin(-localCoord.angle / 180 * Math.PI) * d * Gdx.graphics.getDeltaTime();
		
		if(ammo.size == 0)
		{
			timeToReload -= Gdx.graphics.getDeltaTime();
			if(timeToReload<=0){
				Projectile pro;
				for (int i = 0; i < 5; i++) {
					pro = new Projectile();
					pro.ammoType = ammotype;
					pro.texture = texture;
					ammo.add(pro);
					setText("Ready to fire!", Color.GREEN, 2.0);
				}
				timeToReload = 5;
			}
			
		}
		else
		{
			recoil-=Gdx.graphics.getDeltaTime();
			if(recoil<0 /*&& player.*/)
			{
				localCoord.angle=com.ponycornteam.tools.Trigo.angleCalc(localCoord.x, localCoord.y, player.getX(), player.getY());
				shoot();
				recoil = 2;
			}
		}
	}

	private void shoot() {
		if (ammo.size > 0) {
			Projectile pro = ammo.pop();
			pro.localCoord = new Coord(localCoord.x + standing.getWidth() / 2, localCoord.y + standing.getHeight() / 2);
			/*pro.localCoord.x +=  Math.sin(-localCoord.angle / 360 * Math.PI) * (20);
			pro.localCoord.y +=  Math.cos(-localCoord.angle / 360 * Math.PI) * (20);*/
			pro.localCoord.angle = localCoord.angle;
			pro.speed = 600;
			pro.owner = this;
			GameScreen.projectiles.add(pro);
		}
	}
	
	@Override
	public void colisionObject(direction dir) {
		switch (random.nextInt(3)) {
			case 0:
				localCoord.angle -= 90;
			case 1:
				localCoord.angle -= 90;
			case 2:
				localCoord.angle -= 90;
		}
		if (localCoord.angle < 0)
			localCoord.angle = -localCoord.angle;

	}
}
