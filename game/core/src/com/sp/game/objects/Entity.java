package com.sp.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.Movable;

/**
 * Created by Troy on 10/14/2015.
 */
public abstract class Entity extends GameObject implements Movable {
    protected Rectangle bottom, left, right, top, full;
    protected Texture texture;
    protected Sprite sprite;
    protected int action;
    protected float velocityY;

    public int hits(Rectangle r) {

        if (left.overlaps(r)) {
            return 2;
        }

        if (right.overlaps(r)) {
            return 3;
        }

        if (bottom.overlaps(r)) {
            return 1;
        }

//        if (full.overlaps(r)) {
//            return 1;
//        }

        if (top.overlaps(r)) {
            return 4;
        }

        return -1;
    }


    public void update(float delta) {
        velocityY -= (1000 * delta);
        full.y += velocityY * delta;
//        top.y += velocityY;
//        bottom.y += velocityY;
//        left.y += velocityY;
//        right.y += velocityY;
        setPosition(full.x, full.y);
    }

    public void setPosition(float x, float y) {
        full.x = x;
        full.y = y;

        bottom.x = x;
        bottom.y = y;

        left.x = x;
        left.y = y + 16;

        right.x = x + 64;
        right.y = y + 16;

        top.x = x;
        top.y = y + 112;

        sprite.setPosition(x, y);
    }


    //MOVABLE METHODS
    public void moveLeft(float delta) {
        full.x -= (400 * delta);
//        bottom.x -= (100 * delta);
//        top.x -= (100 * delta);
//        left.x -= (100 * delta);
//        right.x -= (100 * delta);
        setPosition(full.x, full.y);
    }

    public void moveRight(float delta) {
        full.x += (400 * delta);
//        bottom.x += (100 * delta);
//        top.x += (100 * delta);
//        left.x += (100 * delta);
//        right.x += (100 * delta);
        setPosition(full.x, full.y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void jump() {
        if (velocityY == 0)
            velocityY = 700;
    }

    public Rectangle getHitBox() {
        return full;
    }

    public int hitAction(int side) {
        return 0;
    }

    public void resetGravity() {
        velocityY = 0;
    }

}
