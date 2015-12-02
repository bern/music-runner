package com.sp.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 10/16/2015.
 */
public class Collectible extends GameObject {

    Rectangle hitBox;
    Sprite sprite;
    Texture texture;

    public Collectible() {

    }

    public Collectible (int x, int y) {
        hitBox = new Rectangle(x,y,64,64);
        sprite = new Sprite(TextureManager.collectible);

        setPosition(x, y);
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
        return hitBox;
    }

    @Override
    public int hitAction(int side) {
        return 3;   //collect collectible
    }
}
