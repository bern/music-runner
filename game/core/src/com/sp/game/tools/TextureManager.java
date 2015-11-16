package com.sp.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Troy on 11/2/2015.
 */
public class TextureManager {
    //Manages global repository of textures. For performance and code neatness,
    //all art assets are persistent here and will not be created multiple times.
    //We reuse the same textures for all sprites.

    public static Texture platform;
    public static Texture collectible;
    public static Texture avatar;
    public static Texture musicNote;
    public static Texture wave;
    public static Texture volumeBar;
    public static Texture projectile;

    public static void create() {
        platform = new Texture(Gdx.files.internal("img/platform_square_cont64.png"));
        collectible = new Texture(Gdx.files.internal("img/headphones.png"));
        avatar = new Texture(Gdx.files.internal("img/hero.png"));
        musicNote = new Texture(Gdx.files.internal("img/note128.png"));
        wave = new Texture(Gdx.files.internal("img/foreground.png"));
        volumeBar = new Texture(Gdx.files.internal("img/background_alt.png"));
        projectile = new Texture(Gdx.files.internal("img/projectile.png"));
    }

    public static void dispose() {
        platform.dispose();
        collectible.dispose();
        avatar.dispose();
        musicNote.dispose();
        wave.dispose();
        volumeBar.dispose();
        projectile.dispose();
    }


}
