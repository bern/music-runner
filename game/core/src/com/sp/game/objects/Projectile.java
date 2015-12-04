package com.sp.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.Game;
import com.sp.game.tools.TextureManager;
import com.sp.game.tools.TrigUtility;

/**
 * Created by Troy on 11/16/2015.
 */
public class Projectile extends GameObject {

    protected Entity source;
    protected Rectangle hitBox;
    protected Texture texture;
    protected Sprite sprite;
    private ProjectileLifeThread thread;

    TrigUtility trigUtility;

    public Projectile(float sourceX, float sourceY, float destX, float destY) {
        hitBox = new Rectangle(sourceX, sourceY, 32, 32);
        sprite = new Sprite(TextureManager.projectile, 0, 0, 32, 32);
        setPosition(sourceX,sourceY);
        trigUtility = new TrigUtility(sourceX, sourceY, destX, destY);
        thread = new ProjectileLifeThread();
        thread.start();
    }

    @Override
    public int hits(Rectangle r) {
        if (hitBox.overlaps(r)) {
            return 1;
        }
        return 0;
    }

    @Override
    public void action(int type, float x, float y) {

    }

    @Override
    public void update(float delta) {
        if (!thread.alive) {
            Game.getInstance().removeProjectile(this);
        }
        hitBox.setX(hitBox.getX() + trigUtility.getDeltaX() * delta * 1300);
        hitBox.setY(hitBox.getY() + trigUtility.getDeltaY() * delta * 1300);
        setPosition(hitBox.getX(), hitBox.getY());
    }

    @Override
    public void setPosition(float x, float y) {
        hitBox.x = x;
        hitBox.y = y;
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public int hitAction(int side) {
        return 0;
    }

    public float getDeltaX() {
        return trigUtility.getDeltaX();
    }

    public float getDeltaY() {
        return trigUtility.getDeltaY();
    }

    private class ProjectileLifeThread extends Thread {
        public boolean alive = true;

        //thread has a max lifetime for a projectile, that way when one is
        //fired off the screen, it doesn't persist forever
        @Override
        public void run() {
            try {
                Thread.sleep(650);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alive = false;
        }
    }
}
