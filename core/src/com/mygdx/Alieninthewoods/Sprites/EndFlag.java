package com.mygdx.Alieninthewoods.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Screens.PlayScreen;


public class EndFlag extends InteractiveTileObject {
    public EndFlag(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(RunningGame.END_BIT);
        Gdx.app.log("End", "BIT");
    }

    @Override
    public void onHit(com.mygdx.Alieninthewoods.Sprites.Player player) {
        player.die();
    }

    @Override
    public void onHit1(Player1 player) {
        player.die();
    }
}

