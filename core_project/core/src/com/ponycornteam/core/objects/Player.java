package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ponycornteam.core.GameScreen;
import com.ponycornteam.core.objects.Projectile.Ammo;
import com.ponycornteam.tools.Coord;
import com.ponycornteam.tools.Coord.direction;

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
		pshooting.add("Not bad!");
		pshooting.add("This! Is! CANADAAA!");
		pshooting.add("Wow Such cross Very blood So pain Wow");
		pkill.add("U dead bro?");
		pkill.add("LOOOOL");
		pkill.add("In your face!");
		pdead.add("WTF?!");
		eshooting.add("Stop it!");
		eshooting.add("YOU ! SHALL NOT! PASS!");
		eshooting.add("I'm too old for this shit!");
		ekill.add("Surprise motherfucker!");
		ekill.add("In your face");
		edead.add("Fuck");
		edead.add("Are you an angel?");
		edead.add("You will pay for this, bitch!");
		eloosefocus.add("Aaaand she's gone");
		eloosefocus.add("What? Probably just my imagination...");
	}

	public void setMoving(Boolean move) {
		this.move = move;
	}

	public void draw(SpriteBatch batch, float stateTime, Boolean move) {
		this.move = move;
		draw(batch, stateTime);

	}

	public void shoot() {
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
	public void colisionProjectile(Projectile projectile) {
		if (projectile.owner != this && projectile.owner != null && projectile.speed > 0) {
			saying = pdead.random();
			saycolor = Color.RED;
			sayingCount = 10.0;
			dead = true;
			projectile.owner.setText(ekill.random(), Color.ORANGE, 2.5);
			if (sAieWoman != null)
				sAieWoman.random().play();
		} else {
			if (projectile.ammoType == Ammo.palet && projectile.speed < 250) {
				saying = "OH YEAH! FUCKING PUCK";
				saycolor = Color.GREEN;
				sayingCount = 2.5;
				projectile.localCoord = null;
				ammo.add(projectile);
			}
		}
	}

	@Override
	public void colisionObject(direction dir) {
		
	}
}
