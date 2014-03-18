package com.ponycornteam.core.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ponycornteam.tools.Coord;

public interface ICharacter {
	public void setCoord(Coord newCoord);
	public Coord getCoord();
	public Texture getTexture();
}
