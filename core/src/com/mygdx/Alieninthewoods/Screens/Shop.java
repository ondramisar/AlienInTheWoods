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

public class Shop implements Screen {
    private Stage stage;
    private Skin skin;
    private Game game;
    private Viewport viewport;

    private TextButton btn4;
    private TextButton btn1;
    private TextButton btn2;
    private TextButton btn3;
    private TextButton btn5;
    private TextButton btn6;
    private TextButton btn7;
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


    public Shop(final Game game, final AdHandler handler, final Assets assets) {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.game = game;
        this.handler = handler;
        this.assets = assets;

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


        viewport = new FitViewport(RunningGame.V_WIDTH, RunningGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, ((com.mygdx.Alieninthewoods.RunningGame) game).batch);
        Gdx.input.setInputProcessor(stage);

        createBasicSkin("x 2", "x 1");
        btn1 = new TextButton("", skin); // Use the initialized skin
        btn1.setSize(30,30);
        btn1.setPosition(RunningGame.V_WIDTH / 2 - 230, RunningGame.V_HEIGHT / 2 + 110);
        stage.addActor(btn1);
        btn1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Menu(game,handler,assets ));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
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
        btn2.setSize(120,200);
        btn2.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 190, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 80);
        stage.addActor(btn2);
        btn2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        createBasicSkin("buuton 1","button 2");
        btn3 = new TextButton("OPTIONS", skin);
        //** Button text and style **//
        btn3.setSize(120,200);
        btn3.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 60, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 80);
        stage.addActor(btn3);
        btn3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        createBasicSkin("buuton 1","button 2");
        btn4 = new TextButton("OPTIONS", skin);
        //** Button text and style **//
        btn4.setSize(120,200);
        btn4.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 70, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 80);
        stage.addActor(btn4);
        btn4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        createBasicSkin("buuton 1","button 2");
        btn5 = new TextButton("OPTIONS", skin);
        //** Button text and style **//
        btn5.setSize(120,40);
        btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 190, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 130);
        stage.addActor(btn5);
        btn5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                prefs.putString("player", "normal");
                prefs.flush();
            }
        });

        if (prefs.getInteger("coin") > 499) {
            createBasicSkin("buuton 1", "button 2");
            btn6 = new TextButton("OPTIONS", skin);
            //** Button text and style **//
            btn6.setSize(120, 40);
            btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 60, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 130);
            stage.addActor(btn6);
            btn6.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    prefs.putString("player", "normal1");
                    prefs.flush();
                }
            });
        }

        createBasicSkin("buuton 1","button 2");
        btn7 = new TextButton("OPTIONS", skin);
        //** Button text and style **//
        btn7.setSize(120,40);
        btn7.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 70, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 130);
        stage.addActor(btn7);
        btn7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
