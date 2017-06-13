package com.mygdx.Alieninthewoods.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Screens.EndLevel;
import com.mygdx.Alieninthewoods.Screens.PlayScreen;


public class Coin extends InteractiveTileObject{
    public Coin(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(RunningGame.COIN_BIT);
        Gdx.app.log("coin", "BIT");
    }

    @Override
    public void onHit(Player player) {
        setCategoryFilter(RunningGame.NOTHING_BIT);
        getCell().setTile(null);
        Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
        prefs.putInteger("coin", prefs.getInteger("coin") + 1);
        prefs.flush();
    }

    @Override
    public void onHit1(Player1 player) {
        setCategoryFilter(RunningGame.NOTHING_BIT);
        getCell().setTile(null);
        Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
        prefs.putInteger("coin", prefs.getInteger("coin") + 1);
        prefs.flush();
    }
}
