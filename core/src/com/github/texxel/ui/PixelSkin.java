package com.github.texxel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PixelSkin {

    private static final Skin chrome;
    static {
        chrome = new Skin( Gdx.files.internal( "uiskin.json" ) );
    }

    public static Skin chrome() {
        return chrome;
    }

}
