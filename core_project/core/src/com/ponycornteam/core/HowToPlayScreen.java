package com.ponycornteam.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class HowToPlayScreen  implements Screen  {

	final Core game;

    OrthographicCamera camera;

    private Music music; 
    
    private Stage stage;
    private Skin skin;
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
    private Group background; 
    
    
    public HowToPlayScreen(final Core gam) {
    	game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGH);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FileHandle skinFile = Gdx.files.internal( "data/uiskin.json" );
        skin = new Skin( skinFile );
        
        
		final float buttonX = ( game.WIDTH - BUTTON_WIDTH ) / 2;
		 float currentY = 200;
	       // label "welcome"
        Label welcomeLabel = new Label( "Story", skin );
        welcomeLabel.setX(( (  game.WIDTH - welcomeLabel.getWidth() ) / 2 ));
        welcomeLabel.setY(( currentY + 200 ));
        stage.addActor( welcomeLabel );
        
        Label lbl1 = new Label( "The story of a random female hockey player that is in a rotten mood", skin );
        lbl1.setX(( (  game.WIDTH - lbl1.getWidth() ) / 2 ));
        lbl1.setY(( currentY + 170 ));
        stage.addActor( lbl1 );
 
        Label lbl2 = new Label( "because of her period and she just lost her last match.", skin );
        lbl2.setX(( (  game.WIDTH - lbl2.getWidth() ) / 2 ));
        lbl2.setY(( currentY + 140 ));
        stage.addActor( lbl2 );
        
        Label lbl3 = new Label( "How to play", skin );
        lbl3.setX(( (  game.WIDTH - lbl3.getWidth() ) / 2 ));
        lbl3.setY(( currentY + 110 ));
        stage.addActor( lbl3 );
 
        Label lbl4 = new Label( "1. Move with arrows", skin );
        lbl4.setX(( (  game.WIDTH - lbl4.getWidth() ) / 2 ));
        lbl4.setY(( currentY + 90 ));
        stage.addActor( lbl4 );
        
        Label lbl5 = new Label( "2. Shoot with mouse", skin );
        lbl5.setX(( (  game.WIDTH - lbl5.getWidth() ) / 2 ));
        lbl5.setY(( currentY + 70 ));
        stage.addActor( lbl5 );
 
        Label lbl6 = new Label( "3. Survive", skin );
        lbl6.setX(( (  game.WIDTH - lbl6.getWidth() ) / 2 ));
        lbl6.setY(( currentY + 50 ));
        stage.addActor( lbl6 );
        
	        // button "start game"
	        TextButton backButton = new TextButton( "Back to menu", skin );
	        backButton.setX(( (  game.WIDTH - backButton.getWidth() ) / 2 ));
	        backButton.setY(90);
	        backButton.setWidth(BUTTON_WIDTH);
	        backButton.setHeight(BUTTON_HEIGHT);
	        stage.addActor( backButton );
	 
	        
	        backButton.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					game.setScreen(new MainMenuScreen(game));
					dispose(); 
				}
			});
        
    }
    
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		
	}

	@Override
	public void resize(int width, int height) {
		
		stage.setViewport(width, height, true);
	
	        
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

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
		stage.dispose();
		skin.dispose();
	}

}
