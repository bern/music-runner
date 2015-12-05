package com.sp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sp.game.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DesktopLauncher {
	public static void main (String[] arg) {
		try {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.title = "Music Runner";
			config.useGL30 = false;
			config.width = 800;
			config.height = 480;
			new LwjglApplication(new Game(), config);
		}
		catch(Exception e) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File("exception.txt"), true);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			PrintStream ps = new PrintStream(fos);
			e.printStackTrace(ps);
//			e.printStackTrace();
		}
	}
}
