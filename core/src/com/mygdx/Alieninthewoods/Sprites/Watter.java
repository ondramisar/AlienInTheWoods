package com.mygdx.Alieninthewoods.Sprites;


import com.badlogic.gdx.maps.MapObject;

public class Watter extends InteractiveTileObject {
    public Watter(com.mygdx.Alieninthewoods.Screens.PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(com.mygdx.Alieninthewoods.RunningGame.WATER_BIT);
    }

    @Override
    public void onHit(Player player) {

    }

    @Override
    public void onHit1(Player1 player) {

    }

}
