package com.sp.game.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.Game;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 10/14/2015.
 */
public class Avatar extends Entity {

    private Game game;

    private int collectibles = 0;
    private int lives = 5;
    private int ammo = 5;

    private ReloadThread reloadThread = new ReloadThread();      //used to monitor reloading state
    private CoolDownThread cooldownThread = new CoolDownThread();

    public Avatar() {
        full = new Rectangle(0, 0, 128, 128);
        bottom = new Rectangle(0, 0, 128, 16);
        left = new Rectangle(0, 16, 64, 96);
        right = new Rectangle(64, 16, 64, 96);
        top = new Rectangle(0, 112, 128, 16);

        sprite = new Sprite(TextureManager.avatar, 0, 0, 128, 128);
        setPosition(0, 0);
        velocityY = 0;
    }

    public Avatar(Game game) {
        this();
        this.game = game;
    }

    @Override
    public void action(int type, float x, float y) {
        if (type == 1 || type == 4) {
            velocityY = 0;
            setPosition(bottom.x, y);
        }

        if (type == 2 || type == 3) {
            velocityY = 0;
            setPosition(x, bottom.y);
        }
    }

    public void collect() {
        collectibles++;
    }

    public void takeDamage() {
        if (--lives == 0) {
            game.gameOver();
        }
    }

    public void shoot(float x, float y) {
        if (!reloadThread.reloading) {
            if (cooldownThread.ready) {
                if (game.addProjectile(x,y)) {
                    cooldownThread = new CoolDownThread();
                    cooldownThread.ready = false;
                    cooldownThread.start();
                    if (--ammo == 0) {
                        reloadThread = new ReloadThread();
                        reloadThread.reloading = true;
                        reloadThread.start();
                    }
                }
            }

        }

        //if (!reloadThread.reloading && ammo ==0) {
            //reload!
          //  reloadThread.start();
//            new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1500);
//                        reloading = false;
//                        ammo = 5;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.run();
        }
    //}

    public int getLives() {
        return lives;
    }

    public int getCollectibles() {
        return collectibles;
    }

    public int getAmmo() {
        return ammo;
    }

    private class ReloadThread extends Thread {
        public boolean reloading = false;

        @Override
        public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reloading = false;
            ammo = 5;
        }
    }

    private class CoolDownThread extends Thread {
        public boolean ready = true;

        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ready = true;
        }
    }
}
