package com.mygdx.Alieninthewoods.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;
		config.height = 624;
		//new LwjglApplication(new RunningGame(), config);
	}
}
