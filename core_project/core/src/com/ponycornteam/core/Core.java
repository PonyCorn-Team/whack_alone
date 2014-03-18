package com.ponycornteam.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Core extends Game {

	public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager manager; 
	public int WIDTH = 800; 
	public int HEIGH = 480; 
    
    
	@Override
	public void create() {
		manager = new AssetManager(); 
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        this.setScreen(new LoadingScreen(this));
		
	}


    public void render() {
        super.render(); //important!
    }

    public void dispose() {

    }

}
