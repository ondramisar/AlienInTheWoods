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
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Tools.Assets;

public class LevelMenuSelect implements Screen {
    int [] l;
    private int rowCount = 1;
    private int xOffset = 50;
    private int yOffset = 250;
    private Button btn;

    private Viewport viewport;
    private Game game;
    private Stage stage;
    private Skin skin;
    private TextureAtlas buttonsAtlas;

    private int numberOfLevels;

    public static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private int lvlBut = 40;

    private int w,h;

    AdHandler handler;
    Assets assets;

    private Music music;
    private Sound sound;

    private boolean addDisplay = false;

    private Preferences prefs;

    public LevelMenuSelect(final Game game, final AdHandler handler, final Assets assets) {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.game = game;
        this.handler = handler;
        this.assets = assets;


        prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
        music = Assets.manager.get("sounds/music.mp3", Music.class);
        if (prefs.getBoolean("music1")) {
            music.setLooping(true);
            music.play();

        }else {
            music.stop();
        }

        spriteBatch = new SpriteBatch();
        backgroundTexture = Assets.backLevl;

        viewport = new FitViewport(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, ((com.mygdx.Alieninthewoods.RunningGame) game).batch);
        Gdx.input.setInputProcessor(stage);

        buildStage();

        createBasicSkin("buuton 1","button 2");
        TextButton btn1 = new TextButton("back", skin); // Use the initialized skin
        btn1.setSize(100,40);
        btn1.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 150);
        stage.addActor(btn1);
        btn1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Menu(game, handler, assets));

                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

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


    public void buildStage() {
        l = new int[30];

        for(int i = 0; i < l.length; i ++){
            numberOfLevels = i + 1;

            if (prefs.getBoolean(String.valueOf(numberOfLevels))){

            createBasicSkin("pause 1","pause 2");
            btn = new TextButton(String.valueOf(numberOfLevels), skin); // Use the initialized skin

            btn.setWidth(lvlBut);
            btn.setHeight(lvlBut);
            btn.setColor(1, 1, 1,1);
            btn.setX(xOffset);
            btn.setY(yOffset);
            xOffset += 70;
            rowCount += 1;

            if (rowCount > 6 ){
                rowCount = 1;
                xOffset = 50;
                yOffset -= 50;
            }

            stage.addActor(btn);
            final int finalI = i + 1;
            btn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dispose();
                    game.setScreen(new PlayScreen((RunningGame) game, finalI, handler,assets));
                    sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                    final Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
                    if (prefs.getBoolean("music")) {
                        sound.play(0.5f);
                    }else {
                        sound.play(0);
                    }
                }
            });
            }
            else if (i == 0){
                createBasicSkin("pause 1","pause 2");
                TextButton btn = new TextButton(String.valueOf(numberOfLevels), skin); // Use the initialized skin

                btn.setWidth(lvlBut);
                btn.setHeight(lvlBut);
                btn.setColor(1, 1, 1,1);
                btn.setX(xOffset);
                btn.setY(yOffset);
                xOffset += 70;
                rowCount += 1;

                if (rowCount > 6 ){
                    rowCount = 1;
                    xOffset = 50;
                    yOffset -= 50;
                }

                stage.addActor(btn);
                final int finalI = i + 1;
                btn.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dispose();
                        game.setScreen(new PlayScreen((RunningGame) game, finalI, handler,assets));
                        sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                        final Preferences prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");
                        if (prefs.getBoolean("music")) {
                            sound.play(0.5f);
                        }else {
                            sound.play(0);
                        }
                    }
                });
            }
            else {
                createBasicSkin("LOCK 1","LOCK 2");
                TextButton btn = new TextButton("", skin); // Use the initialized skin

                btn.setWidth(lvlBut);
                btn.setHeight(lvlBut);
                btn.setColor(1, 1, 1,1);
                btn.setX(xOffset);
                btn.setY(yOffset);
                xOffset += 70;
                rowCount += 1;

                if (rowCount > 6 ){
                    rowCount = 1;
                    xOffset = 50;
                    yOffset -= 50;
                }

                stage.addActor(btn);
                final int finalI = i + 1;
            }
        }
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0,0,w,h);
        //renderBackground(); //In first place!!!!
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
        skin.dispose();
        spriteBatch.dispose();
    }
}

