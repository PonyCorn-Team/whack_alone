package com.ponycornteam.core;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ponycornteam.core.objects.Enemy;
import com.ponycornteam.core.objects.ICharacter;
import com.ponycornteam.core.objects.IColidable;
import com.ponycornteam.core.objects.IColidableCharacter;
import com.ponycornteam.core.objects.IColidableProjectile;
import com.ponycornteam.core.objects.IDrawable;
import com.ponycornteam.core.objects.Player;
import com.ponycornteam.core.objects.Projectile;
import com.ponycornteam.core.objects.Projectile.Ammo;
import com.ponycornteam.tools.Coord;
import com.ponycornteam.tools.Coord.direction;

public class GameScreen implements Screen, InputProcessor {
	final Core game;

	private Texture cursorImage;
	// private Texture playerImage;

	// private Sprite spritePlayer;
	//private ICharacter player;
	private OrthographicCamera camera;

	private Music music;

	private Player p = null;

	Sound dropSound;
	Music rainMusic;

	BitmapFont font;

	int posX = 0, playerX = 0;
	int posY = 0, playerY = 0;
	int cursorWidth = 0;
	int cursorHeight = 0;

	private FPSLogger fpsLogger;

	boolean isMoving = false;

	private static final int FRAME_COLS = 3; // #1
	private static final int FRAME_ROWS = 1; // #2

	Animation walkAnimation; // #3
	Texture walkSheet; // #4
	TextureRegion[] walkFrames; // #5
	TextureRegion currentFrame; // #7

	float stateTime; // #8

	private OrthogonalTiledMapRenderer renderer;
	private TiledMapTileLayer collisionObjectLayer;
	private MapObjects objects;
	private MapObjects spawnEnnemies;
	private MapObjects oExit;
	private MapObjects oStart;
	boolean isDead = false; 
	
	public static Array<Projectile> projectiles;
	private Array<Enemy> enemy = new Array<Enemy>();

	public GameScreen(final Core gam) {
		projectiles = new Array<Projectile>();
		this.game = gam;

		Texture.setEnforcePotImages(false);

		walkSheet = new Texture(Gdx.files.internal("game/spriteplayer.png")); // #9
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS); // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.025f, walkFrames); // #11
		stateTime = 0f; // #13

		TiledMap map = game.manager.get("game/map/map1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		// int objectLayerId = 3;
		// collisionObjectLayer =
		// (TiledMapTileLayer)map.getLayers().get(objectLayerId);
		// objects = collisionObjectLayer.getObjects();

		objects = map.getLayers().get("owall").getObjects();
		spawnEnnemies = map.getLayers().get("espawn").getObjects();
		oExit = map.getLayers().get("oexit").getObjects();
		oStart = map.getLayers().get("ostart").getObjects();

		// Chargement des sons et musiques
		dropSound = game.manager.get("data/drop.wav", Sound.class);
		rainMusic = game.manager.get("data/rain.mp3", Music.class);
		rainMusic.setLooping(true);

		// create a Rectangle to logically represent the bucket
		Rectangle r = oStart.getByType(RectangleMapObject.class).first().getRectangle();
		p = new Player(game.manager.get("game/01.png", Texture.class), new Coord(r.x, r.y), Ammo.palet, new Sprite(game.manager.get("game/palet.png", Texture.class)));
		p.setMoving(walkAnimation);

		// create the raindrops array and spawn the first raindrop
		// raindrops = new Array<Rectangle>();

		loadSoundAie();
		loadSoundAieWoman();
		loadSoundPaf();
		p.deadSound = game.manager.get("game/dead.wav", Sound.class);
		Texture stand = game.manager.get("game/02.png", Texture.class);
		Sprite bullet = new Sprite(game.manager.get("game/bullet.png", Texture.class));

		Texture walkerSheet = new Texture(Gdx.files.internal("game/spriteenemy.png")); // #9
		TextureRegion[][] tmp2 = TextureRegion.split(walkSheet, walkerSheet.getWidth() / FRAME_COLS, walkerSheet.getHeight() / FRAME_ROWS); // #10
		TextureRegion[] walkFrames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames2[index++] = tmp2[i][j];
			}
		}
		Animation walk = new Animation(0.025f, walkFrames); // #11

		for (RectangleMapObject spawn : spawnEnnemies.getByType(RectangleMapObject.class)) {
			r = spawn.getRectangle();
			enemy.add(new Enemy(stand, new Coord(r.x, r.y), Ammo.bullet, bullet));
			p.setMoving(walk);
		}

		// bounceSound = game.manager.get("game/bounce.wav", Sound.class);
		// shootSound = game.manager.get("game/shoot.wav", Sound.class);

		// playerImage = game.manager.get("game/01.png", Texture.class);
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

		stateTime += Gdx.graphics.getDeltaTime(); // #15
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		p.setAngle(posX, game.HEIGH - posY);

		renderer.setView(camera);
		renderer.render();
		/*
		 * spritePlayer = (isMoving) ? new Sprite(currentFrame) : new
		 * Sprite(player.getTexture());
		 * spritePlayer.setPosition((float)player.getX(), (float)player.getY());
		 * spritePlayer.setRotation((float) player.getAngle());
		 */
		
		
		
	
		if(isDead){
			 game.setScreen(new GameOverScreen(game));
	            dispose();
		}
		
		
		
		game.batch.begin();

		game.font.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 480);
		// if(isMoving)
		// game.batch.draw(currentFrame, player.x, player.y);
		// else
		// game.batch.draw(playerImage, player.x, player.y);
		game.batch.draw(cursorImage, posX - (cursorWidth / 2), game.HEIGH - posY - (cursorHeight / 2));

		// font.draw(game.batch, "hello", (float)(p.getX() + p.getWidth() +10),
		// (float)(p.getY() + p.getHeigh()+10));
		/*
		 * spritePlayer.draw(game.batch); spritePlayer = new
		 * Sprite(pro.texture);
		 */
		for (IDrawable d : projectiles)
			d.draw(game.batch, stateTime);

		for (IDrawable e : enemy)
			e.draw(game.batch, stateTime);

		/*
		 * spritePlayer.setPosition((float)pro.localCoord.x,
		 * (float)pro.localCoord.y); spritePlayer.draw(game.batch);
		 */
		p.draw(game.batch, stateTime, isMoving);

		
		
		game.batch.end();
		
		
		imputEvent();
		// System.out.println("isMoving = " + isMoving );
		// fpsLogger.log();

		// there are several other types, Rectangle is probably the most common
		// one
		Array<IColidable> colidables = new Array<IColidable>();
		colidables.add(p);
		colidables.addAll(enemy);
		for (IColidable p : projectiles) {
			colidables.add(p);
		}

		for (IColidable c : colidables) {
			for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
				Rectangle rectangle = rectangleObject.getRectangle();
				if (Intersector.overlaps(rectangle, c.getRectangle())) {
					if (c.getRectangle().x < rectangle.x + rectangle.width && c.getRectangle().x + c.getRectangle().width > rectangle.x
					// Angie specialy for you a fucking mega over
					// ternaire condition
					// Beacause ternaire is not for fucking NOOB
					// The following fucking over mega ternaire of the
					// world dead who kill every NOOB and your fucking
					// brain too
					// check if the width under the colide box is
					// greater than the height under the colide box
					&& (((c.getRectangle().x > rectangle.x) ? c.getRectangle().x : rectangle.x) - ((c.getRectangle().x + c.getRectangle().getWidth() < rectangle.x + rectangle.width) ? c.getRectangle().x + c.getRectangle().getWidth() : rectangle.x + rectangle.width)) < (((c.getRectangle().y > rectangle.y) ? c.getRectangle().y : rectangle.y) - ((c.getRectangle().y + c.getRectangle().getHeight() < rectangle.y + rectangle.height) ? c.getRectangle().y + c.getRectangle().getHeight() : rectangle.y + rectangle.height))) {
						if (c.getRectangle().y + c.getRectangle().height > rectangle.y && c.getRectangle().y + c.getRectangle().height > rectangle.y + rectangle.height) {
							c.colisionObject(direction.bot);
							c.setY(rectangle.y + rectangle.height);
						} else {
							c.colisionObject(direction.top);
							c.setY(rectangle.y - c.getRectangle().height);
						}

					} else if (c.getRectangle().x > rectangle.x && c.getRectangle().x < rectangle.x + rectangle.width && c.getRectangle().x + c.getRectangle().width > rectangle.x) {
						c.colisionObject(direction.left);
						c.setX(rectangle.x + rectangle.width);
					} else {
						c.colisionObject(direction.right);
						c.setX(rectangle.x - c.getRectangle().width);
					}
				}
			}
		}

		Array<ICharacter> characteres = new Array<ICharacter>();
		characteres.add(p);

		for (Projectile p : projectiles) {
			for (ICharacter c : characteres) {
				Rectangle rectangle = c.getRectangle();
				if (Intersector.overlaps(rectangle, p.getRectangle())) {
					c.colisionProjectile(p);
					p.colisionCharacter(c);
				}
			}
			if ((p.ammoType != Ammo.palet && p.speed < 0) || p.localCoord == null)
				projectiles.removeValue(p, true);

		}

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
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

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
		// System.out.println("screenX = " + screenX + "  screenY = " +
		// screenY);
		// System.out.println("PlayerX = " + player.getX() + "  PlayerY = " +
		// player.getY() + " Angle = " + player.getAngle());

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void loadSoundAie() {
		p.sAie = new Array<Sound>();
		for (int i = 1; i <= 21; i++)
			p.sAie.add(game.manager.get("game/aie/aie" + i + ".wav", Sound.class));

	}

	private void loadSoundAieWoman() {
		p.sAie = new Array<Sound>();
		for (int i = 1; i <= 3; i++)
			p.sAie.add(game.manager.get("game/womanaie/womanaie" + i + ".wav", Sound.class));
	}

	private void loadSoundPaf() {
		p.sPaf = new Array<Sound>();
		for (int i = 1; i <= 4; i++)
			p.sPaf.add(game.manager.get("game/paf/paf" + i + ".mp3", Sound.class));
	}

	private void imputEvent() {
		if (p != null && !p.dead) {
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
				return;
			}

			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				p.shoot();
			}

			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			    isDead = true; 
			}

			if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.DOWN)) {
				if (Gdx.input.isKeyPressed(Keys.LEFT))
					p.setX(p.getX() - 200 * Gdx.graphics.getDeltaTime());

				if (Gdx.input.isKeyPressed(Keys.RIGHT))
					p.setX(p.getX() + 200 * Gdx.graphics.getDeltaTime());

				if (Gdx.input.isKeyPressed(Keys.DOWN))
					p.setY(p.getY() - 200 * Gdx.graphics.getDeltaTime());

				if (Gdx.input.isKeyPressed(Keys.UP))
					p.setY(p.getY() + 200 * Gdx.graphics.getDeltaTime());

				isMoving = true;
				return;

			}

			isMoving = false;
			return;
		}
	}

}
