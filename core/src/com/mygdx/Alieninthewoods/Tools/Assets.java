package com.mygdx.Alieninthewoods.Tools;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    public static AssetManager manager;
    public static TextureAtlas buttonsAtlas;
    public static Texture backgroundTexture;
    public static Texture backLevl;
    public static TextureAtlas player;

    public static void create() {
        manager = new AssetManager();
        load();
    }

    private static void load() {
        manager.load("data/menu clean.png", Texture.class);
        manager.load("data/baclvl.png", Texture.class);
        manager.load("data/button1.pack", TextureAtlas.class);
        //manager.load("data/Player.pack", TextureAtlas.class);
        manager.load("sounds/jump.wav", Sound.class);
        manager.load("sounds/die.wav", Sound.class);
        manager.load("sounds/menu.wav", Sound.class);
        manager.load("sounds/music.mp3", Music.class);
    }

    public static void done() {
        backgroundTexture = manager.get("data/menu clean.png", Texture.class);
        buttonsAtlas = manager.get("data/button1.pack", TextureAtlas.class);
        backLevl = manager.get("data/baclvl.png", Texture.class);
        //player = manager.get("data/Player.pack", TextureAtlas.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}
