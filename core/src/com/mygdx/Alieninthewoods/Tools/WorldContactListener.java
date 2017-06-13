package com.mygdx.Alieninthewoods.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Sprites.InteractiveTileObject;
import com.mygdx.Alieninthewoods.Sprites.Player;
import com.mygdx.Alieninthewoods.Sprites.Player1;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case RunningGame.PLAYER_BIT | RunningGame.END_BIT:
                ((Player) fixB.getUserData()).end();
                Gdx.app.log("end", "BIT");
                break;
            case RunningGame.PLAYER_BIT | RunningGame.WATER_BIT:
                if(fixA.getFilterData().categoryBits == RunningGame.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit(fixB.getUserData());
                else
                    ((Player) fixB.getUserData()).hit(fixA.getUserData());
                break;
            case RunningGame.PLAYER_BIT | RunningGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == RunningGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                break;
            case RunningGame.PLAYER_BIT1 | RunningGame.END_BIT:
                ((Player1) fixB.getUserData()).end();
                Gdx.app.log("end", "BIT");
                break;
            case RunningGame.PLAYER_BIT1 | RunningGame.WATER_BIT:
                if(fixA.getFilterData().categoryBits == RunningGame.PLAYER_BIT1)
                    ((Player1) fixA.getUserData()).hit(fixB.getUserData());
                else
                    ((Player) fixB.getUserData()).hit(fixA.getUserData());
                break;
            case RunningGame.PLAYER_BIT1 | RunningGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == RunningGame.PLAYER_BIT1)
                    ((InteractiveTileObject) fixB.getUserData()).onHit1((Player1) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit1((Player1) fixB.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}

