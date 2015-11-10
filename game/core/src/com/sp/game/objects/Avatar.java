package com.sp.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

/**
 * Created by Troy on 10/14/2015.
 */
public class Avatar extends Entity {

    public Avatar() {
        full = new Rectangle(0, 0, 128, 128);
        bottom = new Rectangle(0, 0, 128, 16);
        left = new Rectangle(0, 16, 64, 96);
        right = new Rectangle(64, 16, 64, 96);
        top = new Rectangle(0, 112, 128, 16);

        sprite = new Sprite(TextureManager.avatar, 0, 0, 128, 128);
        setPosition(0,0);
        velocityY = 0;
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
}
