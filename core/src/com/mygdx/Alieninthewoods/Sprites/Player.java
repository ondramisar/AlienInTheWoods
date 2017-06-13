package com.mygdx.Alieninthewoods.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Screens.EndLevel;
import com.mygdx.Alieninthewoods.Screens.PlayScreen;
import com.mygdx.Alieninthewoods.Tools.Assets;


public class Player extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD, END }

    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion playerStand;
    private Animation playerRun;
    private TextureRegion playerJump;
    private TextureRegion playerDead;

    private float stateTimer;
    private boolean runningRight;
    public boolean playerIsDead;
    private boolean playerIsEnd;
    private PlayScreen screen;

    private BodyDef bdef;
    private Preferences prefs;




    public Player(PlayScreen screen){
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas1().findRegion("p1_spritesheet"), i * 73, 0, 72, 97));
        playerRun = new Animation(0.1f, frames);

        frames.clear();


        //get jump animation frames and add them to marioJump Animation
        playerJump = new TextureRegion(screen.getAtlas1().findRegion("p1_spritesheet"), 438, 93, 72, 97);

        //create texture region for mario standing
        playerStand = new TextureRegion(screen.getAtlas1().findRegion("p1_spritesheet"), 67, 196, 72, 97);

        //create dead mario texture region
        playerDead = new TextureRegion(screen.getAtlas1().findRegion("p1_spritesheet"), 438, 0, 69 , 92);

        //define mario in Box2d
        definePlayer();

        //set initial values for marios location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 20 / RunningGame.PPM, 27 / RunningGame.PPM);
        setRegion(playerStand);


        prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");


    }

    public void update(float dt){
        //update our sprite to correspond with the position of our Box2D body
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);


        //update sprite with the correct frame depending on marios current action
        setRegion(getFrame(dt));

    }

    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = playerDead;
                break;
            case JUMPING:
                region = playerJump;
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(playerIsDead)
            return State.DEAD;
        else if(playerIsEnd)
            return State.END;
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
            //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if mario is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
            //if none of these return then he must be standing
        else
            return State.STANDING;
    }



    public void die() {
        if (!isDead()) {
            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = RunningGame.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            Sound sound = Assets.manager.get("sounds/die.wav", Sound.class);
            final Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
            if (prefs.getBoolean("music")) {
                sound.play(0.5f);
            }else {
                sound.play(0);
            }
        }
    }

    public void end() {
        if (!isDead()) {
            playerIsEnd = true;
        }
    }
    public void hit(Object userData){
        die();
    }
    public boolean isDead(){
        return playerIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 3.4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;


            Sound sound = Assets.manager.get("sounds/jump.wav", Sound.class);
            final Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
            if (prefs.getBoolean("music")) {
                sound.play(0.5f);
            }else {
                sound.play(0);
            }
        }
    }

    public void definePlayer(){
        bdef = new BodyDef();
        bdef.position.set(240 / RunningGame.PPM, 100 / RunningGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearVelocity.set(1.0F, 0.0F);

        //Gdx.app.log("define", "player");

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / RunningGame.PPM);
        fdef.filter.categoryBits = RunningGame.PLAYER_BIT;
        fdef.filter.maskBits = RunningGame.GROUND_BIT |
                RunningGame.WATER_BIT|
                RunningGame.COIN_BIT|
                RunningGame.END_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / RunningGame.PPM, 6 / RunningGame.PPM), new Vector2(2 / RunningGame.PPM, 6 / RunningGame.PPM));

        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
