package com.mygdx.Alieninthewoods.Screens;

import com.badlogic.gdx.Screen;
import com.mygdx.Alieninthewoods.AdHandler;
import com.mygdx.Alieninthewoods.RunningGame;
import com.mygdx.Alieninthewoods.Tools.Assets;


public class Loading implements Screen {

    Assets assets;
    AdHandler handler;
    RunningGame game;
    //Texture background;
    //SpriteBatch spriteBatch;

    public Loading(RunningGame game, AdHandler handler) {
        this.handler = handler;
        this.game = game;

        //spriteBatch = new SpriteBatch();
        //background = new Texture("data/uvodka.png");
        assets = new Assets();
        assets.create();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Assets.manager.update()) {
            Assets.done();
            game.setScreen(new Menu(game, handler,assets)); //your screen should take a Assets object in it's constructor.
        }
    }

    @Override
    public void resize(int width, int height) {

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


    }
}
