package com.ponycornteam.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {

	final Core game;

    OrthographicCamera camera;

    private Music music; 
    
    private Stage stage;
    private Skin skin;
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
    private Group background; 
    
    
    public MainMenuScreen(final Core gam) {
    	game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGH);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FileHandle skinFile = Gdx.files.internal( "data/uiskin.json" );
        skin = new Skin( skinFile );
        
        
        music = game.manager.get("music/548914_Road-Rash---Grass-V.mp3", Music.class);
        music.setLooping(true);

        
        Image background = new Image(new TextureRegion(game.manager.get("menu/menu.png", Texture.class)));
        background.setFillParent(true);
        stage.addActor(background); 
        
		final float buttonX = ( game.WIDTH - BUTTON_WIDTH ) / 2;
		 float currentY = 100;
	       // label "welcome"
     //   Label welcomeLabel = new Label( "Nom du jeu", skin );
    //    welcomeLabel.setX(( (  game.WIDTH - welcomeLabel.getWidth() ) / 2 ));
     //   welcomeLabel.setY(( currentY + 100 ));
      //  stage.addActor( welcomeLabel );
 
        // button "start game"
        TextButton startGameButton = new TextButton( "Start game", skin );
        startGameButton.setX(50);
        startGameButton.setY(100);
        startGameButton.setWidth(BUTTON_WIDTH);
        startGameButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( startGameButton );
 
        // button "options"
        TextButton highScoreButton = new TextButton( "High Score", skin );
        highScoreButton.setX(50);
        highScoreButton.setY(currentY-= BUTTON_HEIGHT + BUTTON_SPACING);
        highScoreButton.setWidth(BUTTON_WIDTH);
        highScoreButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( highScoreButton );
 

        startGameButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game));
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
		// TODO Auto-generated method stub
		music.play();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		music.stop();
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
		music.dispose();
		stage.dispose();
		skin.dispose();
	}

}
