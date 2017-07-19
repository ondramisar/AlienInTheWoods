package com.mygdx.Alieninthewoods.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Alieninthewoods.AdHandler;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Sprites.Player;
import com.mygdx.Alieninthewoods.Sprites.Player1;
import com.mygdx.Alieninthewoods.Tools.Assets;
import com.mygdx.Alieninthewoods.Tools.B2WorldCreator;
import com.mygdx.Alieninthewoods.Tools.WorldContactListener;


public class PlayScreen implements Screen{
    private com.mygdx.Alieninthewoods.RunningGame game;
    private State state;
    private TextureAtlas atlas1;
    private TextureAtlas atlas2;
    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Player player;
    private Player1 player1;


    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private B2WorldCreator creator;

    //variables for creator skin
    private Skin skin;
    private Stage stage;
    private Stage stage1;
    private Viewport viewport;

    //buttons
    private TextButton btn;
    private TextButton btn1;
    private TextButton btn2;
    private TextButton btn3;
    private TextButton btn4;
    private TextButton btn5;
    private TextButton btn6;
    private TextButton btn7;
    private TextButton btn8;

    private TextureAtlas buttonsAtlas;

    private int w, h;

    private int numberOfLevels;

    private Preferences prefs;
    com.mygdx.Alieninthewoods.AdHandler handler;

    Music music;
    Sound sound;


    private boolean addDisplay = false;

    Assets assets;


    public PlayScreen(final RunningGame game, int numberOfLevels, final AdHandler handler, final Assets assets) {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");

       if (prefs.getString("player").equals("normal")){
            atlas1 = new TextureAtlas("data/Player.pack");
        } else if (prefs.getString("player").equals("normal1")){
            atlas2 = new TextureAtlas("data/Player.pack");
        }

       // atlas1 = new TextureAtlas("data/Player.pack");
        //atlas2 = new TextureAtlas("data/Player1.pack");

        this.game = game;
        this.numberOfLevels = numberOfLevels;
        this.handler = handler;
        this.assets = assets;


        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, 30, 20);


        gamePort = new FitViewport(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / com.mygdx.Alieninthewoods.RunningGame.PPM, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / com.mygdx.Alieninthewoods.RunningGame.PPM, gamecam);


        maploader = new TmxMapLoader();
        map = maploader.load("levels/lvl" + String.valueOf(numberOfLevels) + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / com.mygdx.Alieninthewoods.RunningGame.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -13), true);

        world.setContactListener(new WorldContactListener());

        //allows for debug lines of our box2d world.
        //b2dr = new Box2DDebugRenderer();
        //b2dr.SHAPE_STATIC.set(1, 0, 0, 1);

        //B2World
        creator = new B2WorldCreator(this);
        //player creating

        if (prefs.getString("player").equals("normal")){
            player = new Player(this);
        } else if (prefs.getString("player").equals("normal1")){
            player1 = new Player1(this);
        }


        //set state to running
        state =  State.Running;

        // creating stage for buttons in game and stage for pouse

        viewport = new FitViewport(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        stage1 = new Stage(viewport, game.batch);


        createBasicSkin("x 2","x 1");
        //pause menu
        btn1 = new TextButton("", skin); // Use the initialized skin
        btn1.setSize(40, 40);
        btn1.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 100, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30);
        stage1.addActor(btn1);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state == State.Paused){
                    state = State.Running;
                }else if (state == State.Running) {
                    state = State.Paused;
                }
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });


        createBasicSkin("MENU 2","MENU 1");
        btn4 = new TextButton("", skin); // Use the initialized skin
        btn4.setSize(40, 40);
        btn4.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 +20, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 30);
        stage1.addActor(btn4);
        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Menu(game,handler,assets));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });


        createBasicSkin("pause 1","pause 1");
        btn7 = new TextButton("music", skin); // Use the initialized skin
        btn7.setSize(80,40);
        btn7.setPosition(RunningGame.V_WIDTH / 2 - 162, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2+ 12 );
        stage1.addActor(btn7);

        btn8 = new TextButton("Sound", skin); // Use the initialized skin
        btn8.setSize(80,40);
        btn8.setPosition(RunningGame.V_WIDTH / 2 - 78, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2+ 12);
        stage1.addActor(btn8);


        makeSoundButton();
        makeSoundButton1();

        createBasicSkin("OFF 2", "OFF 1");
        //playing buttons
        btn3 = new TextButton("", skin); // Use the initialized skin
        btn3.setSize(40, 40);
        btn3.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 140, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 110);
        stage.addActor(btn3);
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (state == State.Paused){
                    state = State.Running;
                }else if (state == State.Running) {
                    state = State.Paused;
                }
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

        createBasicSkin("pause 1","pause 1");
        //playing buttons
        btn7 = new TextButton("LEVEL:" + numberOfLevels, skin); // Use the initialized skin
        btn7.setSize(70, 40);
        btn7.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 160, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 110);
        stage.addActor(btn7);

        createBasicSkin("button_jump","button_jump");
        btn2 = new TextButton("", skin); // Use the initialized skin
        btn2.setSize(70, 70);
        btn2.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 70, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 150);
        stage.addActor(btn2);
        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getString("player").equals("normal")){
                    player.jump();
                } else if (prefs.getString("player").equals("normal1")){
                    player.jump();
                }
            }
        });
        createBasicSkin("button_run","button_run");
        btn = new TextButton("", skin); // Use the initialized skin
        btn.setSize(70, 70);
        btn.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 150, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 150);
        stage.addActor(btn);
        btn.addListener(new ClickListener() {
            @Override
            public boolean isPressed() {
                return true;
            }
        });




    }
    public void muteButton() {
        createBasicSkin("MUTE 2", "MUTE 1");
        btn5 = new TextButton("", skin); // Use the initialized skin
        btn5.setSize(40, 40);
        btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 60, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30 );
        stage1.addActor(btn5);
        btn5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("music") == false) {
                    prefs.putBoolean("music", true);
                    prefs.flush();
                    createBasicSkin("SOUND 2", "SOUND 1");
                    btn5 = new TextButton("", skin); // Use the initialized skin
                    btn5.setSize(40, 40);
                    btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 60, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30 );
                    stage1.addActor(btn5);
                    btn5.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (prefs.getBoolean("music") == true) {
                                prefs.putBoolean("music", false);
                                prefs.flush();
                                muteButton();

                            }
                        }
                    });
                }
            }
        });
    }

    public void makeSoundButton(){
        if (prefs.getBoolean("music") == true){
            createBasicSkin("SOUND 2", "SOUND 1");
            btn5 = new TextButton("", skin); // Use the initialized skin
            btn5.setSize(40,40);
            btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 60, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30 );
            stage1.addActor(btn5);
            btn5.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (prefs.getBoolean("music") == true) {
                        prefs.putBoolean("music", false);
                        prefs.flush();
                        muteButton();

                    }
                }
            });
        } else {
            muteButton();
        }


    }
    public void muteButton1() {
        createBasicSkin("MUTE 2", "MUTE 1");
        btn6 = new TextButton("", skin); // Use the initialized skin
        btn6.setSize(40, 40);
        btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2- 140, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30 );
        stage1.addActor(btn6);
        btn6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("music1") == false) {
                    prefs.putBoolean("music1", true);
                    prefs.flush();
                    createBasicSkin("SOUND 2", "SOUND 1");
                    btn6 = new TextButton("", skin); // Use the initialized skin
                    btn6.setSize(40, 40);
                    btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2- 140 , com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30 );
                    stage1.addActor(btn6);
                    btn6.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (prefs.getBoolean("music1") == true) {
                                prefs.putBoolean("music1", false);
                                prefs.flush();
                                muteButton1();

                            }
                        }
                    });

                }
            }
        });
    }

    public void makeSoundButton1(){
        if (prefs.getBoolean("music1") == true){
            createBasicSkin("SOUND 2", "SOUND 1");
            btn6 = new TextButton("", skin); // Use the initialized skin
            btn6.setSize(40,40);
            btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2- 140, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-30 );
            stage1
                    .addActor(btn6);
            btn6.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (prefs.getBoolean("music1") == true) {
                        prefs.putBoolean("music1", false);
                        prefs.flush();
                        muteButton1();

                    }
                }
            });
        } else {
            muteButton1();
        }
    }

    public TextureAtlas getAtlas1() {
        return atlas1;
    }

    public TextureAtlas getAtlas2() {
        return atlas2;
    }
    public enum State{
        Running, Paused
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {
        //control our player using immediate impulses
        if (prefs.getString("player").equals("normal")){
            if (player.currentState != com.mygdx.Alieninthewoods.Sprites.Player.State.DEAD) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    player.jump();
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                if (btn.isPressed() == true){
                    if ( player.b2body.getLinearVelocity().x <= 2 ){
                        player.b2body.applyLinearImpulse(new Vector2(0.2f, 0), player.b2body.getWorldCenter(), true);
                    }
                }
            }
        } else if (prefs.getString("player").equals("normal1")){
            /*
            if(player1.currentState != Player1.State.DEAD) {
                if (btn.isPressed() == true) {
                    if (player1.b2body.getLinearVelocity().x <= 2) {
                        player1.b2body.applyLinearImpulse(new Vector2(0.2f, 0), player1.b2body.getWorldCenter(), true);
                    }
                }
            }*/
            if (player.currentState != com.mygdx.Alieninthewoods.Sprites.Player.State.DEAD) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    player.jump();
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                if (btn.isPressed() == true){
                    if ( player.b2body.getLinearVelocity().x <= 2 ){
                        player.b2body.applyLinearImpulse(new Vector2(0.2f, 0), player.b2body.getWorldCenter(), true);
                    }
                }
            }
        }
    }

    private void createBasicSkin(String buttonStile,String buttonStile1) {
        //Create a font
        buttonsAtlas = Assets.buttonsAtlas;
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.addRegions(buttonsAtlas);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(); //** Button properties **//
        textButtonStyle.up = skin.getDrawable(buttonStile);
        textButtonStyle.down = skin.getDrawable(buttonStile1);

        textButtonStyle.font = font;

        skin.add("default", textButtonStyle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // chosing state
        switch(state){
            case Running:
                update(delta);
                draw();
                break;
            case Paused:
                //don't update
                draw1();
                break;
        }

        if (addDisplay) {
            handler.showBannerAd();
        }else {
            handler.hideBannerAd();
        }
        // if game over or end
        if (gameOver()) {
            dispose();
            game.setScreen(new PlayScreen(game, numberOfLevels, handler, assets));

        }
        if (endLvl()) {
            dispose();
            game.setScreen(new EndLevel(game, numberOfLevels, handler, assets));
        }

         /*   handler.showInterstitialAd(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Interstitial app closed");
                    Gdx.app.exit();
                }

        });*/
    }

    public boolean gameOver() {
        if (prefs.getString("player").equals("normal")){
            if(player.currentState == Player.State.DEAD && player.getStateTimer() > 0.3){
                return true;
            }
        } else if (prefs.getString("player").equals("normal1")){
            if (player1.currentState == Player1.State.DEAD && player1.getStateTimer() > 0.3) {
                return true;
            }
        }
        return false;
    }
    public boolean endLvl() {
        if (prefs.getString("player").equals("normal")){
            if (player.currentState == Player.State.END){
                return true;
            }
        } else if (prefs.getString("player").equals("normal1")) {
            if (player1.currentState == Player1.State.END) {
                return true;
            }
        }
        return false;
    }


    public void draw(){
        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if (prefs.getString("player").equals("normal")){
            player.draw(game.batch);
        } else if (prefs.getString("player").equals("normal1")) {
            player1.draw(game.batch);
        }
        game.batch.end();
        //settings input for this buttons
        Gdx.input.setInputProcessor(stage);
        //rendering buttons
        stage.act();
        stage.draw();

    }

    public void draw1(){
        //render our game map
        renderer.render();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if (prefs.getString("player").equals("normal")){
            player.draw(game.batch);
        } else if (prefs.getString("player").equals("normal1")) {
            player1.draw(game.batch);
        }
        game.batch.end();
        //settings input for this buttons
        Gdx.input.setInputProcessor(stage1);
        //rendering buttons
        stage1.act();
        stage1.draw();
    }

    public void update(float delta) {
        //handle user input first
        handleInput(delta);
        //attach our gamecam to our players.x coordinate
        world.step(1 / 60f, 6, 2);
        if (prefs.getString("player").equals("normal")){
            player.update(delta);
        } else if (prefs.getString("player").equals("normal1")) {
            player1.update(delta);
        }

        if (prefs.getString("player").equals("normal")){
            if (player.currentState != Player.State.DEAD) {
                gamecam.position.x = player.b2body.getPosition().x;
            }
        } else if (prefs.getString("player").equals("normal1")) {
            if (player1.currentState != Player1.State.DEAD) {
                gamecam.position.x = player1.b2body.getPosition().x;
            }
        }

            //update our gamecam with correct coordinates after changes
            gamecam.update();
            //tell our renderer to draw only what our camera can see in our game world.
            renderer.setView(gamecam);
            music = Assets.manager.get("sounds/music.mp3", Music.class);
            if (prefs.getBoolean("music1") == true) {
                music.setLooping(true);
                music.play();

            } else {
                music.stop();
            }
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        stage.dispose();
        stage1.dispose();
        skin.dispose();
        if (prefs.getString("player").equals("normal")){
            atlas1.dispose();
        } else if (prefs.getString("player").equals("normal1")) {
            atlas2.dispose();
        }
    }
}
