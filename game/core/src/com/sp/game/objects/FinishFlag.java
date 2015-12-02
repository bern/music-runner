package com.sp.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 12/1/2015.
 */
public class FinishFlag extends Collectible {

    public FinishFlag(int x, int y) {
        super();
        hitBox = new Rectangle(x, y, 128, 512);
        sprite = new Sprite(TextureManager.flag);
        setPosition(x,y);
    }
}
