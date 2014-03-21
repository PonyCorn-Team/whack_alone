package com.ponycornteam.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameJamLogo implements Screen {

	final Core game;
	private Texture splash; 
	private Texture splash2; 
    OrthographicCamera camera;
    boolean isLogo = true; 
    public GameJamLogo(final Core gam) {
    	game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGH);

        splash  =  game.manager.get("splash/logo.png", Texture.class);
        splash2 = game.manager.get("splash/wall.png", Texture.class);
    }
    
	@Override
	public void render(float delta) {


   	    Gdx.gl.glClearColor(95/255, 5/255, 5/255, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(splash, 0, 0, 800,480);
        game.batch.end();

        if (Gdx.input.isTouched()) {
        	if(!isLogo){
            game.setScreen(new SplashScreen(game));
        	}
        	else{
        		splash = splash2; 
        		isLogo = false; 
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
            //dispose();
        }
		
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
}
