package com.ponycornteam.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LoadingScreen implements Screen {

	final Core game;
	private NinePatch empty;
	private NinePatch full;
    OrthographicCamera camera;

    public LoadingScreen(final Core gam) {
    	game = gam;
    	
    	
    	Texture textempty=new Texture(Gdx.files.internal("loading/empty.png"));
    	Texture textfull=new Texture(Gdx.files.internal("loading/full.png"));
    	empty=new NinePatch(new TextureRegion(textempty,24,24),8,8,8,8);
    	full=new NinePatch(new TextureRegion(textfull,24,24),8,8,8,8);

    	
    	game.manager.load("splash/wall.png",Texture.class);
    	//Menu 
    	game.manager.load("menu/menu.wav",Music.class);
    	game.manager.load("menu/menu.png",Texture.class);
    	//Jeu
    	
    		//Music
    			
    		//Sounds
    			loadSoundAie(); 
    			loadSoundAieWoman(); 
    			loadSoundPaf();
    	    	game.manager.load("game/bounce.wav",Sound.class);
    	    	game.manager.load("game/dead.wav",Sound.class);
    	    	game.manager.load("game/shoot.wav",Sound.class);
    			
    		//Pictures
    	game.manager.load("game/01.png",Texture.class);
    	game.manager.load("game/02.png",Texture.class);
    	game.manager.load("game/spriteenemy.png",Texture.class);
    	game.manager.load("game/cursor4040.png", Texture.class);
    	game.manager.load("game/palet.png", Texture.class);
    	game.manager.load("game/bullet.png", Texture.class);
    	game.manager.load("game/dead.png", Texture.class);
    	game.manager.load("game/dead2.png", Texture.class);
    		//map
    	game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
    	game.manager.load("game/map/maptest.tmx", TiledMap.class);
    	game.manager.load("game/map/map1.tmx", TiledMap.class);
    	
    	game.manager.load("data/bucket.png", Texture.class);
    	game.manager.load("data/droplet.png",Texture.class);
    	game.manager.load("data/drop.wav",Sound.class);
    	game.manager.load("data/rain.mp3",Music.class);
    	
    	
    	Texture.setEnforcePotImages(false);
    	
    }
    
	@Override
	public void render(float delta) {

		if(game.manager.update()) {
			game.setScreen(new SplashScreen(game));
	      }
		game.batch.begin();
		empty.draw(game.batch, 40, 225, 720, 30);
		full.draw(game.batch, 40, 225, game.manager.getProgress()*720, 30);
		game.font.drawMultiLine(game.batch,(int)(game.manager.getProgress()*100)+"% loaded",400,247,0, HAlignment.CENTER);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	private void loadSoundAie(){

		for(int i = 1; i <= 21; i++)
			game.manager.load("game/aie/aie"+i+".wav", Sound.class);
	   
	    
	}
	
	private void loadSoundAieWoman(){
		for(int i = 1; i <= 3; i++)
			game.manager.load("game/womanaie/womanaie"+i+".wav", Sound.class);
	}
	
	private void loadSoundPaf(){
		for(int i = 1; i <= 4; i++)
			game.manager.load("game/paf/paf"+i+".mp3", Sound.class);
	}
}
