import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MatlabTestGUI extends JFrame {
	
	private static MatlabTestPanel panel;
	public static boolean play = true;
	
	public MatlabTestGUI(String s) {
		super(s);
		
		this.setSize(640, 480);
		
		panel = new MatlabTestPanel(
			(int)(this.getSize().getWidth()), (int)(this.getSize().getHeight())
		);
		
		add(panel);
	}
	
	public static void main(String[] args) {
		MatlabTestGUI app = new MatlabTestGUI("Test");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		
		Scanner in = new Scanner(System.in);
		String filename = "";
		String difficulty = "";
		
		while(true) {
			System.out.print("Filename to play: ");
			filename = in.next();
			System.out.print("Difficulty (easy medium hard nightmare): ");
			difficulty = in.next();
			try {
				MatlabTest.init(filename, difficulty);
			} catch(Exception e) {
				e.printStackTrace();
			}
				
			while(play) {
				System.out.println("animate");
				panel.animate();
				try {
					Thread.sleep(30);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
