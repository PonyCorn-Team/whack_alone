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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class GameOverScreen  implements Screen {

	final Core game;

    OrthographicCamera camera;

    private Music music; 
    
    private Stage stage;
    private Skin skin;
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
    private Group background; 
    
    int score;
    
    public GameOverScreen(final Core gam) {
    	game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGH);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FileHandle skinFile = Gdx.files.internal( "data/uiskin.json" );
        skin = new Skin( skinFile );
        
        Gdx.input.setCursorCatched(false);
        
       // music = game.manager.get("menu/menu.wav", Music.class);
       // music.setLooping(true);
        
        
        //Image background = new Image(new TextureRegion(game.manager.get("menu/menu.png", Texture.class)));
        //background.setFillParent(true);
       // stage.addActor(background); 
        
        final float buttonX = (  game.WIDTH - BUTTON_WIDTH ) / 2;
        float currentY = 100f;
 
        // label "welcome"
        Label welcomeLabel = new Label( "GAME OVER!", skin );
        welcomeLabel.setX(( (  game.WIDTH - welcomeLabel.getWidth() ) / 2 ));
        welcomeLabel.setY(( currentY + 150 ));
        stage.addActor( welcomeLabel );
 
        
        score = 999;
        // label "welcome"
        Label scorelbl = new Label( "Score : " + score, skin );
        scorelbl.setX(( (  game.WIDTH - scorelbl.getWidth() ) / 2 )  );
        scorelbl.setY(( currentY + 125 ));
        stage.addActor( scorelbl );
        
        Label pseudolbl = new Label( "Pseudo : " + score, skin );
        pseudolbl.setX(( (  game.WIDTH - pseudolbl.getWidth() ) / 2 )  );
        pseudolbl.setY(( currentY + 100 ));
        stage.addActor( pseudolbl );
        
        final TextField textfield = new TextField("", skin);
        textfield.setX(( (  game.WIDTH - textfield.getWidth() ) / 2 ) + pseudolbl.getWidth() );
        textfield.setY(( currentY + 100 ));
        stage.addActor( textfield );
        
        // button "start game"
        TextButton tryAgainButton = new TextButton( "Try again", skin );
        tryAgainButton.setX(buttonX);
        tryAgainButton.setY(currentY);
        tryAgainButton.setWidth(BUTTON_WIDTH);
        tryAgainButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( tryAgainButton );
 
        // button "options"
        TextButton menuButton = new TextButton( "Back to menu", skin );
        menuButton.setX(buttonX);
        menuButton.setY(( currentY -= BUTTON_HEIGHT + BUTTON_SPACING ));
        menuButton.setWidth(BUTTON_WIDTH);
        menuButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( menuButton );

 

        tryAgainButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				String pseudo = textfield.getText();

				game.setScreen(new GameScreen(game));
				dispose();
			}
		});
        
        menuButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				
				String pseudo = textfield.getText();
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
		});
        
        
    }
    
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
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
		stage.dispose();
		skin.dispose();
	}

}