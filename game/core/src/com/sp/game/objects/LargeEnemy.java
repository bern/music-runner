package com.sp.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

public class LargeEnemy extends SmallEnemy {
    
	public LargeEnemy(int x, int y) {
		super(x, y);
		life = 2;
	}
	
    public int getRows() {
    	return 1;
    }
    
    public int getColumns() {
    	return 2;
    }
    
    public Texture getTexture() {
    	return TextureManager.largeEnemy;
    }
    
    public int getAnimLength() {
    	return 2;
    }
    
    public float getWidth() {
    	return 330;
    }
    
    public float getHeight() {
    	return 175;
    }
}
