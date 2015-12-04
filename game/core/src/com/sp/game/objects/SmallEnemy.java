package com.sp.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sp.game.tools.TextureManager;

public class SmallEnemy extends DefaultEnemy {
	
    protected Animation moveAnimation;
    protected TextureRegion[] smallRobotFrames;
    protected TextureRegion curSmallRobotFrame;
    protected float stateTime;
    
	public SmallEnemy(int x, int y) {
		sprite = new Sprite(getTexture(), 0, 0, (int)getWidth(), (int)getWidth());
		hitBox = new Rectangle(x, y, getWidth() / getColumns(), getHeight() / getRows());
		setPosition(x,y);
		TextureRegion[][] tmp = TextureRegion.split(getTexture(), (int)getWidth() / getColumns(), (int)getHeight() / getRows());
        smallRobotFrames = new TextureRegion[getAnimLength()];
        
        boolean flag = true;
        for(int i=0; i < getRows(); ++i) {
        	for(int j = 0; j < getColumns(); j++) {
        		if(((getColumns()*(i))+j) > getAnimLength()) {
        			flag = false;
        			break;
        		}
        		smallRobotFrames[(getColumns()*(i))+j] = tmp[i][j];
        	}
        	if(!flag) {
        		break;
        	}
		}
	
        moveAnimation = new Animation(0.3f, smallRobotFrames);
        stateTime = 0.0f;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
        curSmallRobotFrame = moveAnimation.getKeyFrame(stateTime, true);
        batch.draw(curSmallRobotFrame, getHitBox().getX(), getHitBox().getY());
	}
	
    public int getRows() {
    	return 1;
    }
    
    public int getColumns() {
    	return 4;
    }
    
    public Texture getTexture() {
    	return TextureManager.smallEnemy;
    }
    
    public int getAnimLength() {
    	return 4;
    }
    
    public float getWidth() {
    	return 256;
    }
    
    public float getHeight() {
    	return 100;
    }
}
