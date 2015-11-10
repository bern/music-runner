package com.sp.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

	//MAP OBJECTS
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Entity player;
	private ArrayList<GameObject> list = new ArrayList<GameObject>();
	private ArrayList<GameObject> foreground = new ArrayList<GameObject>();
	private ArrayList<VolumeBar> background = new ArrayList<VolumeBar>();

	//IN GAME CONTROLS & ITEMS
	private Rectangle leftButton, rightButton, jumpButton;
	private Sprite spriteLeft, spriteRight, spriteJump;
	private Texture buttonTexture;

	//MAIN MENU ITEMS
	private Rectangle mainMenuLogin, mainMenuStart, mainMenuQuit;
	private Sprite mainMenuLoginSprite, mainMenuStartSprite, mainMenuQuitSprite;
	private Texture mainMenuTexture;

	private List<GameObject> deleteList = new ArrayList<GameObject>();
	private int gameState = 1; 	//1 = Main menu, 2 = In game, 3 = game finish, 4 = game over


	@Override
	public void create () {

		//CREATE TEXTURE MANAGER
		TextureManager.create();

		//CONFIGURE CAMERA AND SPRITE BATCH
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		//CONFIGURE AVATAR
		player = new Avatar();
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

		//CONFIGURE MAIN MENU SPRITES
		mainMenuTexture = new Texture(Gdx.files.internal("img/mainmenuitems.png"));
		mainMenuLoginSprite = new Sprite(mainMenuTexture, 0, 0, 256, 128);
		mainMenuStartSprite = new Sprite(mainMenuTexture, 256, 0, 256, 128);
		mainMenuQuitSprite = new Sprite(mainMenuTexture, 0, 128, 256, 128);

		mainMenuLoginSprite.setPosition(40, 150);
		mainMenuStartSprite.setPosition(320, 150);
		mainMenuQuitSprite.setPosition(600, 150);

		mainMenuLogin = new Rectangle(mainMenuLoginSprite.getX(), mainMenuLoginSprite.getY(), 256, 128);
		mainMenuStart = new Rectangle(mainMenuStartSprite.getX(), mainMenuStartSprite.getY(), 256, 128);
		mainMenuQuit = new Rectangle(mainMenuQuitSprite.getX(), mainMenuQuitSprite.getY(), 256, 128);

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
				list.add(new MusicNote(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
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

		updateCamera();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		switch (gameState) {
			case 1:
				mainMenu();
				break;
			case 2:
				mainGame();
				break;
			case 3:
				gameFinish();
				break;
			case 4:
				gameOver();
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
		leftButton.x = player.getHitBox().x - 380 + 350;
		spriteLeft.setPosition(leftButton.x, 20);

		rightButton.x = player.getHitBox().x -280 + 350;
		spriteRight.setPosition(rightButton.x, 20);

		jumpButton.x = player.getHitBox().x + 312 + 350;
		spriteJump.setPosition(jumpButton.x, 20);

		camera.position.x = player.getHitBox().x + 350;
		camera.update();
	}

	public void mainMenu() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

//		player.draw(batch);
//		for (GameObject obj: list) {
//			obj.draw(batch);
//		}

		mainMenuLoginSprite.draw(batch);
		mainMenuStartSprite.draw(batch);
		mainMenuQuitSprite.draw(batch);


		batch.end();

		camera.position.x = 400;
		camera.position.y = 240;


		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			Rectangle touch = new Rectangle(touchPos.x -16, touchPos.y - 16, 32, 32);

			if (touch.overlaps(mainMenuStart)) {
				gameState = 2;
			}
			else if (touch.overlaps(mainMenuLogin)) {

			}
			else if (touch.overlaps(mainMenuQuit)) {
				Gdx.app.exit();
			}
		}
		



	}

	public void mainGame() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (VolumeBar o: background) {
			o.draw(batch);
		}

		for(GameObject o: foreground) {
			o.draw(batch);
		}

		player.draw(batch);
		for (GameObject obj: list) {
			obj.draw(batch);
		}

		spriteLeft.draw(batch);
		spriteRight.draw(batch);
		spriteJump.draw(batch);

		batch.end();

		//UPDATES

		player.update(Gdx.graphics.getDeltaTime());
		Rectangle temp = new Rectangle(0, 0, 800, 10);

		if (player.hits(temp) != -1)
			player.action(1, 0, 10);

		for(GameObject obj: list) {
			switch (player.hits(obj.getHitBox())) {
				case 1:
					switch(obj.hitAction(1)) {
						case 1:
							player.action(1, 0, obj.getHitBox().y + obj.getHitBox().height);
							break;
						case 2:
							player.setPosition(0, 400);
							player.resetGravity();
							break;
						case 3:
							deleteList.add(obj);
							break;
					}
					break;
				case 2:
					switch(obj.hitAction(2)) {
						case 1:
							float distance = player.getHitBox().getX() - (obj.getHitBox().x + obj.getHitBox().width + 1);
							player.action(2, obj.getHitBox().x + obj.getHitBox().width + 1, 0);

							for (VolumeBar v: background) {
								v.action(0, distance, 0);
							}
							break;
						case 2:
							player.setPosition(0, 400);
							player.resetGravity();
							break;
						case 3:
							deleteList.add(obj);
					}
					break;
				case 3:
					switch(obj.hitAction(3)) {
						case 1:
							float distance = player.getHitBox().getX() - (obj.getHitBox().x - player.getHitBox().width - 1);
							player.action(3, obj.getHitBox().x - player.getHitBox().width - 1, 0);

							for (VolumeBar v: background) {
								v.action(0, distance, 0);
							}
							break;
						case 2:
							player.setPosition(0, 400);
							player.resetGravity();
							break;
						case 3:
							deleteList.add(obj);
							break;
					}
					break;
				case 4:
					switch(obj.hitAction(4)) {
						case 1:
							player.action(4, 0, obj.getHitBox().y - player.getHitBox().height);
							break;
						case 2:
							player.setPosition(0, 400);
							player.resetGravity();
							break;
						case 3:
							deleteList.add(obj);
							break;
					}
					break;
			}
		}

		while(!deleteList.isEmpty()) {
			list.remove(deleteList.get(0));
			deleteList.remove(0);
		}


		//CONTROLS
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.moveLeft(Gdx.graphics.getDeltaTime());
			for (VolumeBar v: background)
				v.moveLeft(Gdx.graphics.getDeltaTime());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.moveRight(Gdx.graphics.getDeltaTime());
			for (VolumeBar v: background)
				v.moveRight(Gdx.graphics.getDeltaTime());
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
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
			}
		}

		updateCamera();


	}

	public void gameFinish() {

	}

	public void gameOver() {

	}
}
