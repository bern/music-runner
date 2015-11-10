package com.sp.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 11/2/2015.
 */
public class Wave extends GameObject {

    Sprite sprite;

    public Wave(float x, float y) {
        sprite = new Sprite(TextureManager.wave);
        this.setPosition(x,y);
    }

    @Override
    public int hits(Rectangle r) {
        return 0;
    }

    @Override
    public void action(int type, float x, float y) {

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
}
