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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

public class Options implements Screen  {
    private Stage stage1;
    private Stage stage;

    private Skin skin;
    private Game game;
    private Viewport viewport;

    private TextButton btn;
    private TextButton btn3;
    private TextButton btn5;
    private TextButton btn6;
    private TextButton btn7;
    private TextButton btn8;
    private TextureAtlas buttonsAtlas;

    public static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private int w,h;
    Preferences prefs;

    Sound sound;
    Music music;

    AdHandler handler;
    private State state;
    private SpriteBatch batch;
    FreeTypeFontGenerator generator;
    BitmapFont font12;

    GlyphLayout glyphLayout;
    GlyphLayout glyphLayout1;
    GlyphLayout glyphLayout2;

    Assets assets;
    public Options(final Game game, final AdHandler handler, final Assets assets) {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.game = game;
        this.handler = handler;
        this.assets = assets;

        state = State.Running;
        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraftia.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        if (w > 1500) {
            parameter.size = 32;
        } else if (w > 1000 && w < 1500){
            parameter.size = 25;
        } else if (w > 500 && w < 1000){
            parameter.size = 15;
        } else if (w > 200 && w < 500){
            parameter.size = 8;
        }
        font12 = generator.generateFont(parameter); // font size 12 pi// xels

        String text = "Music";
        String text1 = "Sound";
        String textOfHowTo = "Gameplay is simple left button is for run,\n" +
                "right button is for jump.\n" +
                "\n" +
                "You are trying not to jump in to the watter\n" +
                "or from high\n" +
                "\n" +
                "Sometimes you will need to slow down\n" +
                "before you jump,\n" +
                "there are some tricks that you will learn by\n" +
                "progressing in a game";

        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font12, text);

        glyphLayout2 = new GlyphLayout();
        glyphLayout2.setText(font12, text1);

        glyphLayout1 = new GlyphLayout();
        glyphLayout1.setText(font12,textOfHowTo);

        prefs = com.mygdx.Alieninthewoods.Screens.EndLevel.prefs = Gdx.app.getPreferences("pref");


        spriteBatch = new SpriteBatch();
        backgroundTexture = assets.backgroundTexture;

        viewport = new FitViewport(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, ((com.mygdx.Alieninthewoods.RunningGame) game).batch);
        stage1 = new Stage(viewport, ((com.mygdx.Alieninthewoods.RunningGame) game).batch);



        createBasicSkin("buuton 1", "button 2");
        btn = new TextButton("HOW TO", skin); // Use the initialized skin
        btn.setSize(100,40);
        btn.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 50);
        stage.addActor(btn);
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                state = State.HOWTO;
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                if (prefs.getBoolean("music")) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });


        btn3 = new TextButton("HELP", skin); // Use the initialized skin
        btn3.setSize(100,40);
        btn3.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 );
        stage.addActor(btn3);
        btn3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                state = State.HELP;
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
        btn7.setPosition(RunningGame.V_WIDTH / 2 + 53, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 50);
        stage.addActor(btn7);

        btn8 = new TextButton("Sound", skin); // Use the initialized skin
        btn8.setSize(80,40);
        btn8.setPosition(RunningGame.V_WIDTH / 2 - 134, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 - 50);
        stage.addActor(btn8);



        if (prefs.getBoolean("music1")){
            prefs.putBoolean("music1", true);
            prefs.flush();
        }else if (!prefs.getBoolean("music1")){
            prefs.putBoolean("music1", false);
            prefs.flush();
        }
        if (prefs.getBoolean("music")){
            prefs.putBoolean("music", true);
            prefs.flush();
        }else if (!prefs.getBoolean("music")){
            prefs.putBoolean("music", false);
            prefs.flush();
        }

        makeSoundButton();
        makeSoundButton1();


        createBasicSkin("x 2", "x 1");
        TextButton btn1 = new TextButton("", skin); // Use the initialized skin
        btn1.setSize(30,30);
        btn1.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 130, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 60);
        stage.addActor(btn1);
        btn1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new Menu(game,handler,assets));
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                final Preferences prefs = com.mygdx.Alieninthewoods.Screens.EndLevel.prefs = Gdx.app.getPreferences("pref");
                if (prefs.getBoolean("music") == true) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

        createBasicSkin("x 2", "x 1");
        TextButton btn3 = new TextButton("", skin); // Use the initialized skin
        btn3.setSize(30,30);
        btn3.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 130, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2 + 60);
        stage1.addActor(btn3);
        btn3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                state = State.Running;
                sound = Assets.manager.get("sounds/menu.wav", Sound.class);
                final Preferences prefs = com.mygdx.Alieninthewoods.Screens.EndLevel.prefs = Gdx.app.getPreferences("pref");
                if (prefs.getBoolean("music") == true) {
                    sound.play(0.5f);
                }else {
                    sound.play(0);
                }
            }
        });

        music = Assets.manager.get("sounds/music.mp3", Music.class);
        if (prefs.getBoolean("music1") == true) {
            music.setLooping(true);
            music.play();

        }else {
            music.stop();
        }


    }

    public enum State{
        Running, HOWTO,HELP,RATE
    }

    public void muteButton() {
        createBasicSkin("MUTE 2", "MUTE 1");
        btn5 = new TextButton("", skin); // Use the initialized skin
        btn5.setSize(40, 40);
        btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-50 );
        stage.addActor(btn5);
        btn5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("music") == false) {
                    prefs.putBoolean("music", true);
                    prefs.flush();
                    createBasicSkin("SOUND 2", "SOUND 1");
                    btn5 = new TextButton("", skin); // Use the initialized skin
                    btn5.setSize(40, 40);
                    btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-50 );
                    stage.addActor(btn5);
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
            btn5.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 - 50, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-50 );
            stage.addActor(btn5);
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
        btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2+ 10, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-50 );
        stage.addActor(btn6);
        btn6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("music1") == false) {
                    prefs.putBoolean("music1", true);
                    prefs.flush();
                    createBasicSkin("SOUND 2", "SOUND 1");
                    btn6 = new TextButton("", skin); // Use the initialized skin
                    btn6.setSize(40, 40);
                    btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2+ 10 , com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-50 );
                    stage.addActor(btn6);
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
            btn6.setPosition(com.mygdx.Alieninthewoods.RunningGame.V_WIDTH / 2 + 10, com.mygdx.Alieninthewoods.RunningGame.V_HEIGHT / 2-50 );
            stage.addActor(btn6);
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0,0,w,h);
        //renderBackground(); //In first place!!!!
        spriteBatch.end();
        switch(state){
            case Running:
                draw();
                break;
            case HOWTO:
                //don't update
                draw1();
                break;
            case HELP:
                //don't update
                draw2();
                break;
        }
    }

    public void draw(){
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
    }

    public void draw1() {
        Gdx.input.setInputProcessor(stage1);
        stage1.act();
        stage1.draw();
        batch.begin();
        float wOfGly = glyphLayout1.width;
        float hOfGly = glyphLayout1.height;
        font12.draw(batch,glyphLayout1, (w-wOfGly) / 2 ,(h+hOfGly/2) / 2);
        batch.end();
    }

    public void draw2() {
        Gdx.input.setInputProcessor(stage1);
        stage1.act();
        stage1.draw();
        batch.begin();
        font12.draw(batch, "For any help contact me here:\n" +
                " ondramisar124@seznam.cz",  (w - w/2) /2 ,(h + h/3) /2);
        batch.end();
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
        batch.dispose();
        spriteBatch.dispose();
        stage.dispose();
        stage1.dispose();
        skin.dispose();
        generator.dispose();
    }
}
