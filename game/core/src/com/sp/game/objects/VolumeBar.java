package com.sp.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.Movable;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 11/2/2015.
 */
public class VolumeBar extends GameObject implements Movable{

    Sprite sprite;

    public VolumeBar(float x, float y, float size) {
        sprite = new Sprite(TextureManager.volumeBar);
        sprite.setSize(300 * size, 300 * size);
        sprite.setPosition(x,y);
    }

    @Override
    public int hits(Rectangle r) {
        return 0;
    }

    @Override
    public void action(int type, float x, float y) {
        setPosition(sprite.getX() - x/2, sprite.getY());
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void setPosition(float x, float y) {
        sprite.setPosition(x,y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public Rectangle getHitBox() {
        return null;
    }

    @Override
    public int hitAction(int side) {
        return 0;
    }

    //MOVABLE METHODS
    public void moveRight(float delta) {
        int speed = 200;
        setPosition(sprite.getX() + (speed * delta), sprite.getY());
    }

    public void moveLeft(float delta) {
        int speed = 200;
        setPosition(sprite.getX() - (speed * delta), sprite.getY());
    }
}
