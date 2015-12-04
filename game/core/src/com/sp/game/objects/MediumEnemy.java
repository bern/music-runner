package com.sp.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

public class MediumEnemy extends SmallEnemy {
    
	public MediumEnemy(int x, int y) {
		super(x, y);
	}
	
	@Override
    public int getRows() {
    	return 1;
    }
    
    @Override
    public int getColumns() {
    	return 2;
    }
    
    @Override
    public Texture getTexture() {
    	return TextureManager.mediumEnemy;
    }
    
    @Override
    public int getAnimLength() {
    	return 2;
    }
    
    @Override
    public float getWidth() {
    	return 201;
    }
    
    @Override
    public float getHeight() {
    	return 150;
    }
}
