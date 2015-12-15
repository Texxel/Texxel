package com.github.texxel.sprites.api;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class to provide an empty texture region for custom renderers to use
 */
public class EmptyTexture {

    private static final TextureRegion texture;
    static {
        Pixmap pixmap = new Pixmap( 2, 2, Pixmap.Format.RGBA8888 );
        Pixmap.setBlending( Pixmap.Blending.None );
        pixmap.setColor( 0, 0, 0, 0 );
        pixmap.fill();
        Pixmap.setBlending( Pixmap.Blending.SourceOver );
        texture = new TextureRegion( new Texture( pixmap ) );
    }

    public static TextureRegion instance() {
        return texture;
    }
}
