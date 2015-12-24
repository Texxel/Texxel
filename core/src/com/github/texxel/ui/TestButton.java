package com.github.texxel.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TestButton extends Image {

    public TestButton() {
        super( new TextureRegion( new Texture( "amulet.png" ) ) );
    }
}
