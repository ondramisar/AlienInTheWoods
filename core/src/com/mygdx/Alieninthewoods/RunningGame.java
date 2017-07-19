package com.mygdx.Alieninthewoods;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.Alieninthewoods.Screens.EndLevel;
import com.mygdx.Alieninthewoods.Screens.Loading;

/* This code is updated version that is not on google play store
Some new features doesnt work, need to completly rewrite a player class
shop is not working
coins doesnt work

 */


public class RunningGame extends Game {
	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 308;
	public static final float PPM = 100;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short END_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short PLAYER_BIT1 = 16;
	public static final short WATER_BIT = 64;

	public static final String NAME = "Explosions";
	public static final String VERSION = "0.8.2";
	public static boolean debug = false;

	public SpriteBatch batch;
	AdHandler handler;

	public RunningGame(AdHandler handler){
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();


		setScreen(new Loading(this, handler));

		Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");

		prefs.putString("player", "normal");
		prefs.flush();
	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}