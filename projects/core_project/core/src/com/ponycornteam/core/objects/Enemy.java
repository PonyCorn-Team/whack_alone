package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ponycornteam.core.objects.Projectile.Ammo;
import com.ponycornteam.tools.Coord;

public class Enemy extends Character {
	public Enemy(Texture stand, Coord coord, Ammo ammotype, Sprite texture) {
		localCoord = coord;
		this.standing = new Sprite(stand);
		width = stand.getWidth();
		heigth = stand.getHeight();
		if(Ammo.bullet == ammotype)
		{
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
}
