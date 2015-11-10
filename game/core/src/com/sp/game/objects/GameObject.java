package com.sp.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Troy on 10/14/2015.
 */
public abstract class GameObject {
    public abstract int hits(Rectangle r);
    public abstract void action(int type, float x, float y);
    public abstract void update(float delta);
    public abstract void setPosition(float x, float y);
    public abstract void draw(SpriteBatch batch);
    public abstract Rectangle getHitBox();
    public abstract int hitAction(int side);
}
