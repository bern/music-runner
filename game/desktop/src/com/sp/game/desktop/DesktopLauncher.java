package com.sp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sp.game.Game;

import java.io.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Music Runner: Killer Beats";
		config.useGL30 = false;
		config.width = 800;
		config.height = 480;
		writeGame();
		new LwjglApplication(new Game(), config);
	}

	public static void writeGame() {
		//TO BE REFACORED ELSEWHERE
		File file = new File("levels/level2.txt");
		FileOutputStream os;
		BufferedOutputStream out;
		try {
			 os = new FileOutputStream(file);

			out = new BufferedOutputStream(os);
			for(int i=0; i < 500; ++i) {
				out.write("Platform ".getBytes());
				int cur = 64 * i;
				String string = String.valueOf(cur);
				out.write(string.getBytes());
				out.write(" 0\n".getBytes());
				if (i % 4 == 0) {
					out.write("Wave ".getBytes());
					out.write(string.getBytes());
					out.write(" 48\n".getBytes());
				}
				if (i % 10 == 0 && i != 0) {
					out.write("MusicNote ".getBytes());
					out.write(string.getBytes());
					out.write(" 64\n".getBytes());
				}

				if (i % 10 == 5 && i != 0) {
					out.write("Collectible ".getBytes());
					out.write(string.getBytes());
					out.write(" 256\n".getBytes());
				}

				int bgCur;

			}
			for (int j=0; j < 500 / 5; ++j) {
				int bgCur = 600 * j;
				String string = String.valueOf(bgCur);
				out.write("VolumeBar ".getBytes());
				out.write(string.getBytes());
				out.write(" -200 2\n".getBytes());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}




	}
}
