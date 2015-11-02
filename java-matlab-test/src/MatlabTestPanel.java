import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MatlabTestPanel extends JPanel{
	
	private static ArrayList<Enemy> enemyList;
	private static int contentWidth = 0;
	private static int contentHeight = 0;
	
	private static File enemyImage;
	
	MatlabTestPanel(int width, int height) {
		setBackground(Color.BLACK);
		
		contentWidth = width;
		contentHeight = height;
		
		enemyList = new ArrayList<Enemy>();
	
		// JFileChooser fc = new JFileChooser();
		// fc.showOpenDialog(this);
		// enemyImage = fc.getSelectedFile();
		// System.out.println(enemyImage);
		
		enemyImage = new File("C:\\Users\\bern\\Documents\\Code\\Eclipse\\matlab-test\\src\\enemy.png");
	}
	
	public static void addEnemy() {
		int speed = ((int)(Math.random()*8))+3;
		enemyList.add(
			new Enemy(enemyImage, contentHeight, contentWidth, speed)
		);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = 0; i < enemyList.size(); i++) {
			Enemy temp = enemyList.get(i);
			g.drawImage(temp.getSprite(), temp.getX(), temp.getY(), this);
			temp.move();
			enemyList.set(i, temp);
			if(temp.outOfBounds()) {
				enemyList.remove(i);
				i--;
			}
		}
	}
	
	public void animate() {
		repaint();
	}
}
