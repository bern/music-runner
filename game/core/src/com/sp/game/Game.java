package com.sp.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sp.game.objects.*;
import com.sp.game.tools.Movable;
import com.sp.game.tools.TextureManager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;

public class Game implements ApplicationListener {

	private static Game game;

	//MAP OBJECTS
	private OrthographicCamera camera;		//viewport
	private SpriteBatch batch;				//collection of sprites
	private Avatar player;					//our player
	private ArrayList<GameObject> list = new ArrayList<GameObject>();		//game objects  such as enemies
	private ArrayList<MusicNote> enemies = new ArrayList<MusicNote>();
	private ArrayList<GameObject> foreground = new ArrayList<GameObject>();	//foreground objects that move with camera
	private ArrayList<VolumeBar> background = new ArrayList<VolumeBar>();	//background objects that move slower than camera
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	//IN GAME CONTROLS & ITEMS
	private Rectangle leftButton, rightButton, jumpButton;	//mobile controles
	private Sprite spriteLeft, spriteRight, spriteJump;		//sprites for mobile controls
	private Texture buttonTexture;		//paints sprites to texture

	//MAIN MENU ITEMS
	private Rectangle mainMenuLogin, mainMenuStart, mainMenuQuit;
	private Sprite mainMenuLoginSprite, mainMenuStartSprite, mainMenuQuitSprite;
	private Texture mainMenuTexture;

	private List<GameObject> deleteList = new ArrayList<GameObject>();		//items queued to be deleted
	private int gameState = 1; 	//1 = Main menu, 2 = In game, 3 = game finish, 4 = game over

	//Game screen fonts
	private BitmapFont welcome;
	private BitmapFont lives;
	private BitmapFont collectibles;
	private BitmapFont ammo;
	private float fontPos = 0;		//For font "following" avatar


	@Override
	public void create () {

		game = this;

		//CREATE TEXTURE MANAGER
		TextureManager.create();

		//CONFIGURE CAMERA AND SPRITE BATCH
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		//CONFIGURE AVATAR
		player = new Avatar(this);
		player.setPosition(100, 200);

		//CONFIGURE MOBILE CONTROLS
		buttonTexture = new Texture(Gdx.files.internal("img/arrows.png"));
		spriteLeft = new Sprite(buttonTexture, 0, 0, 64, 64);
		spriteRight = new Sprite(buttonTexture, 64, 0, 64, 64);
		spriteJump = new Sprite(buttonTexture, 64, 64, 64, 64);
		leftButton = new Rectangle(20, 20, 64, 64);
		rightButton = new Rectangle(120, 20, 64, 64);
		jumpButton = new Rectangle(716, 20, 64, 64);

		spriteLeft.setPosition(20, 20);
		spriteRight.setPosition(120, 20);
		spriteJump.setPosition(716, 20);

		//CONFIGURE BITMAP FONTS
		welcome = new BitmapFont(Gdx.files.internal("font/font.fnt"),
				Gdx.files.internal("font/font.png"), false);
		welcome.getData().setScale(1.2f);

		lives = new BitmapFont(Gdx.files.internal("font/font.fnt"),
				Gdx.files.internal("font/font.png"), false);
		lives.getData().setScale(1);

		//CONFIGURE MAIN MENU SPRITES
		mainMenuTexture = new Texture(Gdx.files.internal("img/mainmenuitems.png"));
		mainMenuLoginSprite = new Sprite(mainMenuTexture, 0, 0, 256, 128);
		mainMenuStartSprite = new Sprite(mainMenuTexture, 256, 0, 256, 128);
		mainMenuQuitSprite = new Sprite(mainMenuTexture, 0, 128, 256, 128);

		//set position of main menu buttons
		mainMenuLoginSprite.setPosition(40, 150);
		mainMenuStartSprite.setPosition(320, 150);
		mainMenuQuitSprite.setPosition(600, 150);

		//set "hitboxes" of main menu buttons
		//note: rectangles support "overlap" function, used to power events with buttons
		mainMenuLogin = new Rectangle(mainMenuLoginSprite.getX(), mainMenuLoginSprite.getY(), 256, 128);
		mainMenuStart = new Rectangle(mainMenuStartSprite.getX(), mainMenuStartSprite.getY(), 256, 128);
		mainMenuQuit = new Rectangle(mainMenuQuitSprite.getX(), mainMenuQuitSprite.getY(), 256, 128);

		//LOAD LEVEL ALGORITHM
		FileHandle file = Gdx.files.internal("levels/level2.txt");
		StringTokenizer tokens = new StringTokenizer(file.readString());
		while (tokens.hasMoreTokens()) {
			String type = tokens.nextToken();
			if (type.equals("Platform")) {
				list.add(new Platform(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}
			else if (type.equals("MusicNote")) {
				MusicNote note = new MusicNote(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken()));
				list.add(note);
				enemies.add(note);
			}
			else if(type.equals("Collectible")) {
				list.add(new Collectible (
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}
			else if (type.equals("Wave")) {
				foreground.add(new Wave(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}
			else if (type.equals("VolumeBar")) {
				background.add(new VolumeBar(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}
		}

//		list.add(new Platform(0, 0));
//		list.add(new Platform(64,0));
//		list.add(new Platform(128,0));
//		list.add(new Platform(256,128));
//		list.add(new Platform(320,128));
//		list.add(new MusicNote(400, 10));

		updateCamera();		//init camera to starting game location
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		//Game screen 'logic'. Could/should be encapsulated in libGDX Screen objects
		switch (gameState) {
			case 1:
				mainMenu();
				break;
			case 2:
				mainGame();
				break;
			case 3:
				gameFinish();	//to be implemented
				break;
			case 4:
				gameOver();		//to be implemented
				break;

		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		TextureManager.dispose();
	}

	public void updateCamera() {
		//mobile controls
		leftButton.x = player.getHitBox().x - 380 + 350;
		spriteLeft.setPosition(leftButton.x, 20);

		rightButton.x = player.getHitBox().x -280 + 350;
		spriteRight.setPosition(rightButton.x, 20);

		jumpButton.x = player.getHitBox().x + 312 + 350;
		spriteJump.setPosition(jumpButton.x, 20);

		//camera position
		camera.position.x = player.getHitBox().x + 350;
		camera.update();
	}

	public void mainMenu() {

		//INIT MAIN MENU AND BUTTONS
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//ALLOW DRAWING OF TEXTURES
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//DRAW SPIRTES TO BATCH
		mainMenuLoginSprite.draw(batch);
		mainMenuStartSprite.draw(batch);
		mainMenuQuitSprite.draw(batch);

		batch.end();

		//FOR POSITIONING REASONS
		camera.position.x = 400;
		camera.position.y = 240;

		//CLICK HANDLER
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			Rectangle touch = new Rectangle(touchPos.x -16, touchPos.y - 16, 32, 32);

			if (touch.overlaps(mainMenuStart)) {
				//start game
				gameState = 2;
			}
			else if (touch.overlaps(mainMenuLogin)) {
				//probably won't do anything here
			}
			else if (touch.overlaps(mainMenuQuit)) {
				//exit application
				Gdx.app.exit();
			}
		}
	}

	public void mainGame() {
		//set background to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//init camera and batch
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//draw background
		for (VolumeBar o: background) {
			o.draw(batch);
		}

		//draw foreground
		for(GameObject o: foreground) {
			o.draw(batch);
		}

		//draw player and immediate objects
		player.draw(batch);
		for (GameObject obj: list) {
			obj.draw(batch);
		}

		for (Projectile p: projectiles) {
			p.draw(batch);
		}

		//draw mobile controls
		spriteLeft.draw(batch);
		spriteRight.draw(batch);
		spriteJump.draw(batch);

		welcome.draw(batch, "Welcome to Music Runner", 20, 350);
		lives.draw(batch, "Lives: " + player.getLives(), player.getHitBox().getX() - 20, 450);
		lives.draw(batch, "Coins: " + player.getCollectibles(), player.getHitBox().getX() + 280, 450);
		lives.draw(batch, "Ammo: " + player.getAmmo(), player.getHitBox().getX() + 580, 450);


		batch.end();

		//UPDATES
		//handles physics and position
		player.update(Gdx.graphics.getDeltaTime());
		for (Projectile p: projectiles) {
			p.update(Gdx.graphics.getDeltaTime());
		}
		Rectangle temp = new Rectangle(0, 0, 800, 10);

		//this was for testing I believe - probably don't need
		if (player.hits(temp) != -1)
			player.action(1, 0, 10);

		//physics logic
		for(GameObject obj: list) {
			switch (player.hits(obj.getHitBox())) {
				case 1:		//it hit the BOTTOM box
					switch(obj.hitAction(1)) {
						case 1:		//a regular "landing", no effect other than positioning
							player.action(1, 0, obj.getHitBox().y + obj.getHitBox().height);
							break;
						case 2:		//player's bottom hit enemy- kill them!
							deleteList.add(obj);
							player.jump(200);
							break;
						case 3:		//player hit collectible = add to delete list
							deleteList.add(obj);
							break;
					}
					break;
				case 2:		//it hit the LEFT box
					switch(obj.hitAction(2)) {
						case 1:
							float distance = player.getHitBox().getX() - (obj.getHitBox().x + obj.getHitBox().width + 1);
							player.action(2, obj.getHitBox().x + obj.getHitBox().width + 1, 0);

							for (VolumeBar v: background) {
								v.action(0, distance, 0);
							}
							break;
						case 2:		//our left his enemy- we lose a life
							player.takeDamage();
							deleteList.add(obj);
							//player.setPosition(0, 400);
							//player.resetGravity();
							break;
						case 3:		//collect item
							deleteList.add(obj);
					}
					break;
				case 3:		//it hit the RIGHT box
					switch(obj.hitAction(3)) {
						case 1:		//side landing
							float distance = player.getHitBox().getX() - (obj.getHitBox().x - player.getHitBox().width - 1);
							player.action(3, obj.getHitBox().x - player.getHitBox().width - 1, 0);

							for (VolumeBar v: background) {
								v.action(0, distance, 0);
							}
							break;
						case 2:		//our right hit enemy- we die
							player.takeDamage();
							deleteList.add(obj);
							//player.setPosition(0, 400);
							//player.resetGravity();
							break;
						case 3:		//collect item
							deleteList.add(obj);
							break;
					}
					break;
				case 4: //it hit the TOP box
					switch(obj.hitAction(4)) {
						case 1:		//our head hit object
							player.action(4, 0, obj.getHitBox().y - player.getHitBox().height);
							break;
						case 2:		//our head hit enemy- we die
							player.takeDamage();
							deleteList.add(obj);
							//player.setPosition(0, 400);
							//player.resetGravity();
							break;
						case 3:		//collect item
							deleteList.add(obj);
							break;
					}
					break;
			}
		}

		//Check for projectile collisions.
		for(Projectile p: projectiles) {
			for (MusicNote mn: enemies) {
				if (p.hits(mn.getHitBox()) > 0) {
					deleteList.add(p);
					deleteList.add(mn);
				}
			}
		}
		//any objects that have been "killed" or taken
		while(!deleteList.isEmpty()) {
			GameObject obj = deleteList.get(0);
			if (obj instanceof Collectible) {
				player.collect();
			}
			if (obj instanceof Projectile) {
				projectiles.remove(obj);
			}
			if (obj instanceof MusicNote) {
				enemies.remove(obj);
			}
			list.remove(obj);
			deleteList.remove(0);
		}


		//CONTROLS
		//move left
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.moveLeft(Gdx.graphics.getDeltaTime());
			//paralax scrolling
			for (VolumeBar v: background)
				v.moveLeft(Gdx.graphics.getDeltaTime());
		}
		//move right
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveRight(Gdx.graphics.getDeltaTime());
			//paralax scrolling
			for (VolumeBar v: background)
				v.moveRight(Gdx.graphics.getDeltaTime());
		}
		//jump
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W))
			player.jump();

		for (int i=0; i<5; ++i) {
			if (Gdx.input.isTouched(i)) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touchPos);
				Rectangle touch = new Rectangle(touchPos.x -16, touchPos.y - 16, 32, 32);

				if(touch.overlaps(leftButton)) {
					player.moveLeft(Gdx.graphics.getDeltaTime());
					for (VolumeBar v: background)
						v.moveLeft(Gdx.graphics.getDeltaTime());
				}
				if(touch.overlaps(rightButton)) {
					player.moveRight(Gdx.graphics.getDeltaTime());
					for (VolumeBar v: background)
						v.moveRight(Gdx.graphics.getDeltaTime());
				}
				if(touch.overlaps(jumpButton)) {
					player.jump();
				}
				//TO DO: CREATE PROJECTILE
				else {
					//System.out.println("Shot at " + touch.x + ", " + touch.y);
					player.shoot(touch.getX(), touch.getY());
//					projectiles.add(new Projectile(
//							player.getHitBox().getX() + 110,
//							player.getHitBox().getY() + 65,
//							touch.getX(),
//							touch.getY()
//							));
				}
			}
		}

		updateCamera();


	}

	public void addProjectile(float x, float y) {
		projectiles.add(new Projectile(
				player.getHitBox().getX() + 110,
				player.getHitBox().getY() + 65,
				x,
				y
		));

	}

	public void gameFinish() {
		//TODO
	}

	public void gameOver() {
		gameState = 1;
	}

	public Avatar getPlayer() {
		return player;
	}

	public static Game getInstance() {
		return game;
	}

	public synchronized void removeProjectile(Projectile projectile) {
		if (projectiles.contains(projectile)) {
			deleteList.add(projectile);
		}
	}
}
