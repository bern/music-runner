package com.sp.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 11/16/2015.
 */
public class Projectile extends GameObject {

    protected Entity source;
    protected Rectangle hitBox;
    protected Texture texture;
    protected Sprite sprite;

    public Projectile(int x, int y) {
        hitBox = new Rectangle(x, y, 32, 32);
        sprite = new Sprite(TextureManager.projectile, 0, 0, 32, 32);
        setPosition(x,y);
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
        hitBox.x = x;
        hitBox.y = y;
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
        return 0;
    }
}
