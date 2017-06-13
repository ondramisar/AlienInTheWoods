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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Alieninthewoods.AdHandler;
import com.mygdx.Alieninthewoods.Tools.Assets;

public class EndLevel implements Screen {
    public static Preferences prefs;
    private Viewport viewport;
    private Stage stage;
    private Button btn;
    private Skin skin;
    private TextureAtlas buttonsAtlas;

    private Game game;

    public static Texture backgroundTexture;
    private SpriteBatch spriteBatch;
    private int numberOfLevels;
    private int w,h;

    private int buttonsSize = 50;

    com.mygdx.Alieninthewoods.AdHandler handler;


    Sound sound;
    Music music;
    Assets assets;


    public EndLevel(final Game game, final int numberOfLevels, final AdHandler handler, final Assets assets){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.game = game;
        this.numberOfLevels = numberOfLevels;
        this.handler = handler;
        this.assets = assets;

        spriteBatch = new SpriteBatch();
        backgroundTexture = Assets.backgroundTexture;

        viewport = new FitViewport(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, ((com.mygdx.Alieninthewoods.RunningGame) game).batch);
        Gdx.input.setInputProcessor(stage);

        prefs = Gdx.app.getPreferences("pref");



        createBasicSkin("MENU 2","MENU 1");
        btn = new TextButton("", skin); // Use the initialized skin
        btn.setSize(buttonsSize,buttonsSize);
        btn.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 30);
        stage.addActor(btn);
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Menu(game,handler,assets));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music") == true) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

        createBasicSkin("BACK 2","BACK 1");
        TextButton btn1 = new TextButton("", skin); // Use the initialized skin
        btn1.setSize(buttonsSize,buttonsSize);
        btn1.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 30, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2- 30);
        stage.addActor(btn1);
        btn1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PlayScreen((com.mygdx.Alieninthewoods.RunningGame) game, numberOfLevels, handler, assets));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music") == true) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

        createBasicSkin("next 2","next1");
        TextButton btn2 = new TextButton("", skin); // Use the initialized skin
        btn2.setSize(buttonsSize,buttonsSize);
        btn2.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 110, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2- 30);
        stage.addActor(btn2);
        btn2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PlayScreen((com.mygdx.Alieninthewoods.RunningGame) game, numberOfLevels + 1,handler, assets));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music") == true) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }


            }
        });

        prefs.putBoolean(String.valueOf(numberOfLevels + 1), true);
        prefs.flush();
        if (numberOfLevels > 17){
            prefs.putBoolean(String.valueOf(numberOfLevels + 1), false);
            prefs.flush();
        }


        music = Assets.manager.get("sounds/music.mp3", Music.class);
        if (prefs.getBoolean("music1") == true) {
            music.setLooping(true);
            music.play();

        }else {
            music.stop();
        }

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0,0,w,h);
        //renderBackground(); //In first place!!!!
        spriteBatch.end();
        stage.act();
        stage.draw();
        handler.showBannerAd();
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
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        spriteBatch.dispose();
        stage.dispose();
        skin.dispose();
    }
}
