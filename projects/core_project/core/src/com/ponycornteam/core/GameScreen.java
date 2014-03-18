package com.ponycornteam.core;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen, InputProcessor{
    final Core game;

    private Texture cursorImage; 
    private Texture playerImage; 
    
    private Rectangle player;
    private OrthographicCamera camera;
    
    
    private Music music; 
    
    private Array<Sound>  sAie;
    private Array<Sound>  sAieWoman;
    private Array<Sound>  sPaf;
    
    private Sound deadSound;
    private Sound bounceSound;
    private Sound shootSound; 
    
    
    
    Sound dropSound;
    Music rainMusic;
    

    
    
    int dropsGathered;

    
    
    int posX = 0, playerX = 0; 
    int posY = 0, playerY = 0; 
    int cursorWidth = 0; 
    int cursorHeight = 0; 
    
    private FPSLogger fpsLogger;
    
    boolean isMoving = false; 
    
    
    private static final int    FRAME_COLS = 3;     // #1
    private static final int    FRAME_ROWS = 1;     // #2

    Animation           walkAnimation;      // #3
    Texture             walkSheet;      // #4
    TextureRegion[]         walkFrames;     // #5
    TextureRegion           currentFrame;       // #7

    float stateTime;                    // #8
    
    
    
    
    
    
    public GameScreen(final Core gam) {
        this.game = gam;

        Texture.setEnforcePotImages(false);

        
        
        
        walkSheet = new Texture(Gdx.files.internal("game/spriteplayer.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.025f, walkFrames);      // #11
        stateTime = 0f;                         // #13
        
        
        
        
        
        

        //Chargement des sons et musiques 
        dropSound =  game.manager.get("data/drop.wav", Sound.class);
        rainMusic =   game.manager.get("data/rain.mp3", Music.class);
        rainMusic.setLooping(true);



        // create a Rectangle to logically represent the bucket
         player = new Rectangle();
         player.x = 800 / 2 - 64 / 2; // center the bucket horizontally
         player.y = 20; // bottom left corner of the bucket is 20 pixels above
                        // the bottom screen edge
         player.width = 64;
         player.height = 64;

        // create the raindrops array and spawn the first raindrop
        //raindrops = new Array<Rectangle>();

         loadSoundAie();
         loadSoundAieWoman();
         loadSoundPaf(); 
          deadSound =  game.manager.get("game/dead.wav", Sound.class);
          bounceSound =  game.manager.get("game/bounce.wav", Sound.class);
          shootSound =  game.manager.get("game/shoot.wav", Sound.class); 
         
         
        playerImage = game.manager.get("game/01.png", Texture.class);
        cursorImage = game.manager.get("game/cursor4040.png", Texture.class); 
         
        cursorWidth = cursorImage.getWidth(); 
        cursorHeight = cursorImage.getHeight(); 
        
        
        // Création de la caméra 
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGH);
        
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCursorCatched(true);

    }

    
    
    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        
        
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  
        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        if(isMoving)
        	game.batch.draw(currentFrame, player.x, player.y);  
        else
        	game.batch.draw(playerImage, player.x, player.y);
        game.batch.draw(cursorImage, posX - (cursorWidth / 2), game.HEIGH - posY - (cursorHeight / 2));
        
       
        
        game.batch.end();

      
        imputEvent(); 
        System.out.println("isMoving = " + isMoving );
        //fpsLogger.log();
        
        if (player.x < 0)
        	player.x = 0;
        if (player.x > game.WIDTH - 64)
        	player.x = game.WIDTH - 64;
        if (player.y < 0)
        	player.y = 0;
        if (player.y > game.HEIGH - 64)
        	player.y = game.HEIGH - 64;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    	cursorImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		posX = screenX; 
		posY = screenY; 
		//System.out.println("screenX = " + screenX + "  screenY = " + screenY );
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadSoundAie(){
		sAie = new Array<Sound>(); 
		for(int i = 1; i <= 21; i++)
			sAie.add(game.manager.get("game/aie/aie"+i+".wav", Sound.class));
	   
	    
	}
	
	private void loadSoundAieWoman(){
		sAieWoman = new Array<Sound>(); 
		for(int i = 1; i <= 3; i++)
			sAieWoman.add(game.manager.get("game/womanaie/womanaie"+i+".wav", Sound.class));
	}
	
	private void loadSoundPaf(){
		sPaf = new Array<Sound>(); 
		for(int i = 1; i <= 4; i++)
			sPaf.add(game.manager.get("game/paf/paf"+i+".mp3", Sound.class));
	}
	
	private void imputEvent(){
		
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
	        	Gdx.app.exit(); 
	        	return;
	        }
		
	       	if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
	        	
	        }
	        
	        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
	        	
	        }
	        
	        
	        
	        if(Gdx.input.isKeyPressed(Keys.LEFT) ||  Gdx.input.isKeyPressed(Keys.RIGHT)||  Gdx.input.isKeyPressed(Keys.UP)||  Gdx.input.isKeyPressed(Keys.DOWN) ){
	        	if (Gdx.input.isKeyPressed(Keys.LEFT))
		            player.x -= 200 * Gdx.graphics.getDeltaTime(); 

		        if (Gdx.input.isKeyPressed(Keys.RIGHT))
		        	player.x += 200 * Gdx.graphics.getDeltaTime();
		        	
		        if (Gdx.input.isKeyPressed(Keys.DOWN))
		            player.y -= 200 * Gdx.graphics.getDeltaTime();
		           
		        if (Gdx.input.isKeyPressed(Keys.UP))
		        	player.y += 200 * Gdx.graphics.getDeltaTime();
		        	
		        isMoving = true;
	        	return;
		        
	        }
	        
	        isMoving = false; 
	        return; 
	}
	
}
