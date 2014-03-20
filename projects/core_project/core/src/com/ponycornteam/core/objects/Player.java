package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ponycornteam.core.GameScreen;
import com.ponycornteam.core.objects.Projectile.Ammo;
import com.ponycornteam.tools.Coord;

public class Player extends Character {

	public Player(Texture standing, Coord coord, Ammo ammotype, Sprite texture) {
		localCoord = coord;
		this.standing = new Sprite(standing);
		width = standing.getWidth();
		heigth = standing.getHeight();

		Projectile pro = new Projectile();
		pro.ammoType = ammotype;
		pro.texture = texture;
		ammo.add(pro);
	}

	public void setMoving(Boolean move) {
		this.move = move;
	}

	public void draw(SpriteBatch batch, float stateTime, Boolean move) {
		this.move = move;
		draw(batch, stateTime);

	}

	public void setText(String txt, Color txtColor, Double timeShowing) {
		saying = txt;
		saycolor = txtColor;
		sayingCount = (timeShowing > 0) ? timeShowing : 5;
	}

	public void shoot() {
		if (ammo.size > 0) {
			Projectile pro = ammo.pop();
			pro.localCoord = new Coord(0, 0);
			pro.localCoord.x = localCoord.x + Math.cos(-localCoord.angle / 180 * Math.PI) * (standing.getWidth());
			pro.localCoord.y = localCoord.y + Math.sin(-localCoord.angle / 180 * Math.PI) * (standing.getHeight() / 2);
			pro.localCoord.angle = localCoord.angle;
			pro.speed = 600;
			pro.owner = this;
			GameScreen.projectiles.add(pro);
		}
	}

	@Override
	public void colisionProjectile(Projectile projectile) {
		if (projectile.owner != this && projectile.owner != null && projectile.speed > 0) {
			saying = "shit i'm dead!";
			saycolor = Color.RED;
			sayingCount = 10.0;
		} else {
			if (projectile.ammoType == Ammo.palet && projectile.speed < 250) {
				saying = "YEAH AMMO!";
				saycolor = Color.PINK;
				sayingCount = 2.5;
				projectile.localCoord = null;
				ammo.add(projectile);
			} else {
				saying = "pfiou it's mine!";
				saycolor = Color.GREEN;
				sayingCount = 2.5;
			}
		}

	}
}
