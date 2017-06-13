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

public class Credit implements Screen {

    private Stage stage;
    private Skin skin;
    private Game game;
    private Viewport viewport;

    private TextButton btn1;
    private TextureAtlas buttonsAtlas;
    public static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private int w,h;
    Preferences prefs;
    Sound sound;
    com.mygdx.Alieninthewoods.AdHandler handler;
    private SpriteBatch batch;

    Music music;
    FreeTypeFontGenerator generator;
    BitmapFont font12;

    String text;
    private Assets assets;


    public Credit(final Game game, final AdHandler handler, final Assets assets) {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        //Gdx.app.log("MyTag", String.valueOf(w));

        this.game = game;
        this.handler = handler;
        this.assets= assets;

        prefs = EndLevel.prefs = Gdx.app.getPreferences("pref");

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

        text =  "Graphic for menu, options and buttons \n" +
        "fb: Berserker Design\n"
        + "\n"
        +"Programming done by Ondrej Misar \n" +
        "contact on: ondramisar124@seznam.cz \n" +
        "Twitter: @ondra_misar\n"
        + "\n"
        +"Level graphic is from free assets: www.kenney.nl";




        batch = new SpriteBatch();


        spriteBatch = new SpriteBatch();

        backgroundTexture = assets.backgroundTexture;

        viewport = new FitViewport(RunningGame.V_WIDTH, RunningGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, ((RunningGame) game).batch);
        Gdx.input.setInputProcessor(stage);


        createBasicSkin("x 2", "x 1");
        btn1 = new TextButton("", skin); // Use the initialized skin
        btn1.setSize(30,30);
        btn1.setPosition(RunningGame.V_WIDTH / 2 - 130, RunningGame.V_HEIGHT / 2 + 60);
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


        music = Assets.manager.get("sounds/music.mp3", Music.class);
        if (prefs.getBoolean("music1")) {
            music.setLooping(true);
            music.play();

        }else {
            music.stop();
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

        stage.act();
        stage.draw();

        batch.begin();

        font12.draw(batch, text, (w - w/2) /2 ,(h + h/3) /2);
        batch.end();

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
        batch.dispose();
        spriteBatch.dispose();
        stage.dispose();
        skin.dispose();
        generator.dispose();
    }
}
