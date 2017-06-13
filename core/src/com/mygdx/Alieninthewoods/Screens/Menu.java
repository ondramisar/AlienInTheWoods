package com.mygdx.Alieninthewoods.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Alieninthewoods.AdHandler;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Tools.Assets;

public class Menu implements Screen {
    private Stage stage;
    private Skin skin;
    private Game game;
    private Viewport viewport;

    private TextButton btn;
    private TextButton btn1;
    private TextButton btn2;
    private TextButton btn3;
    private TextButton btn4;
    private TextButton btn5;
    private TextureAtlas buttonsAtlas;
    public static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private int w,h;

    Sound sound;
    Music music;

    public AdHandler handler;
    public boolean addDisplay = true;

    private Preferences prefs;
    private Assets assets;
    private State state;

    Menu(final Game game, final AdHandler handler, final Assets assets) {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.game = game;
        this.handler = handler;
        this.assets = assets;

        state = State.Running;

        spriteBatch = new SpriteBatch();

        backgroundTexture = assets.backgroundTexture;

        prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
        music = Assets.manager.get("sounds/music.mp3", Music.class);
        if (prefs.getBoolean("music1")) {
            music.setLooping(true);
            music.play();

        }else {
            music.stop();
        }

        if (prefs.getString("player") == null){
            prefs.putString("player", "normal");
            prefs.flush();
        }

        if (prefs.getInteger("coin") == 0){
            prefs.putInteger("coin", 0);
            prefs.flush();
        }


        viewport = new FitViewport(RunningGame.V_WIDTH, RunningGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, ((com.mygdx.Alieninthewoods.RunningGame) game).batch);
        Gdx.input.setInputProcessor(stage);

        createBasicSkin("buuton 1","button 2");
        btn4 = new TextButton(String.valueOf(prefs.getInteger("coin")), skin);
        //** Button text and style **//
        btn4.setSize(100,40);
        btn4.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 100, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 50);
        stage.addActor(btn4);

        createBasicSkin("buuton 1","button 2");
        btn5 = new TextButton("ad", skin);
        //** Button text and style **//
        btn5.setSize(100,40);
        btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 100, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 );
        stage.addActor(btn5);
        btn5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putInteger("coin", prefs.getInteger("coin") + 20);
                prefs.flush();
                System.out.println(prefs.getInteger("coin"));
                state = State.ADS_DISPLAY;
            }
        });

        createBasicSkin("buuton 1","button 2");
        btn = new TextButton("PLAY", skin);
        //** Button text and style **//
        btn.setSize(100,40);
        btn.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 50);
        stage.addActor(btn);
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new LevelMenuSelect(game, handler,assets));
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                final Preferences prefs = com.mygdx.Alieninthewoods.Screens.EndLevel.prefs = Gdx.app.getPreferences("pref");
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }

            }
        });

        createBasicSkin("buuton 1","button 2");
        btn1 = new TextButton("SHOP", skin);
        //** Button text and style **//
        btn1.setSize(100,40);
        btn1.setPosition(RunningGame.V_WIDTH / 2 - 50,RunningGame.V_HEIGHT / 2 );
        stage.addActor(btn1);
        btn1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Shop(game,handler,assets));
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                final Preferences prefs = com.mygdx.Alieninthewoods.Screens.EndLevel.prefs = Gdx.app.getPreferences("pref");
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });


        createBasicSkin("buuton 1","button 2");
        btn2 = new TextButton("OPTIONS", skin);
        //** Button text and style **//
        btn2.setSize(100,40);
        btn2.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 50);
        stage.addActor(btn2);
        btn2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Options(game, handler,assets));
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                final Preferences prefs = com.mygdx.Alieninthewoods.Screens.EndLevel.prefs = Gdx.app.getPreferences("pref");
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

        btn3 = new TextButton("CREDIT", skin);
        //** Button text and style **//
        btn3.setSize(100,40);
        btn3.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 100);
        stage.addActor(btn3);
        btn3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Credit(game,handler, assets));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });
    }

    public enum State{
        Running, ADS_DISPLAY
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        switch (state){
            case ADS_DISPLAY:
                if (handler.isWifiConnected()) {
                    handler.showInterstitialAd(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Interstitial app closed");
                            Gdx.app.exit();
                            state = State.Running;
                        }
                    });
                } else {
                    System.out.println("Interstitial ad not (yet) loaded");
                }
                break;
            case Running:

                spriteBatch.begin();
                spriteBatch.draw(backgroundTexture, 0,0,w,h);
                spriteBatch.end();

                stage.act();
                stage.draw();

                if (addDisplay) {
                    handler.showBannerAd();
                }else {
                    handler.hideBannerAd();
                }
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
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
    public void show() {

    }
    private void createBasicSkin(String buttonStile,String buttonStile1) {
        //Create a font
        buttonsAtlas = assets.buttonsAtlas;
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();

    }
}