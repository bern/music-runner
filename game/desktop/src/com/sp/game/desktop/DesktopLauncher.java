package com.sp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sp.game.Game;
import com.sp.game.tools.FramesLevelBuilder;

import java.io.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Music Runner: Killer Beats";
		config.useGL30 = false;
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Game(), config);
	}
}
