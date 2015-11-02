import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Enemy {
	
	private BufferedImage sprite = null;
	
	private int x = 0;
	private int y = 0;
	private int speed = 0;
	private int boundary = 0;
	
	public Enemy(File imageFile, int height, int width, int speed) {
		try{
			sprite = ImageIO.read(imageFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		y = (int)(Math.random()*height);
		
		if(y < 20) {
			y = 20;
		}
		else if(y > (height-20)) {
			y = height-20;
		}
		
		this.speed = speed;
		this.boundary = width;
	}
	
	public void move() {
		x = x+speed;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public boolean outOfBounds() {
		return x > boundary;
	}
}
