package com.github.texxel.mechanics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * A simple implementation of the FogOfWar interface
 */
public class SimpleFog implements FogOfWar {

    private static final long serialVersionUID = 2017304830112013067L;

    final int width, height;
    transient Pixmap pixmap;
    transient Sprite sprite;
    transient Texture texture;
    transient private boolean dirty = false;

    public SimpleFog( int width, int height ) {
        if ( width <= 0 || height <= 0 )
            throw new IllegalArgumentException( "width and height must be > 0. Passed width="
                    + width + " height=" + height );
        width++;
        height++;
        this.width = width;
        this.height = height;

        init();
    }

    private void readObject( ObjectInputStream inputStream ) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        init();
    }

    private void init() {
        int textureWidth = 2;
        int textureHeight = 2;
        while ( textureWidth < width )
            textureWidth <<= 1;
        while ( textureHeight < height )
            textureHeight <<= 1;

        pixmap = new Pixmap( textureWidth, textureHeight, Pixmap.Format.RGBA8888 );
        Pixmap.setBlending( Pixmap.Blending.None );
        pixmap.setColor( 0, 0, 0, 0 );
        pixmap.fill();
        Pixmap.setBlending( Pixmap.Blending.SourceOver );

        texture = new Texture( pixmap );

        sprite = new Sprite( texture = new Texture( pixmap ));
        sprite.setPosition( -0.5f, -0.5f );
        // must flip the y axis because drawn texture uses the opposite y axis convention
        sprite.setScale( 1, -1 );
        sprite.setTexture( texture );

        dirty = true;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void setColor( int x, int y, int color ) {
        Pixmap.setBlending( Pixmap.Blending.None );
        pixmap.drawPixel( x, y - 1, color );
        dirty = true;
        Pixmap.setBlending( Pixmap.Blending.SourceOver );
    }

    @Override
    public int getColor( int x, int y ) {
        return pixmap.getPixel( x, y );
    }

    @Override
    public void setColorAroundTile( int x, int y, int color ) {
        Pixmap.setBlending( Pixmap.Blending.None );
        pixmap.setColor( color );
        pixmap.drawRectangle( x, y, 2, 2 );
        dirty = true;
        Pixmap.setBlending( Pixmap.Blending.SourceOver );
    }

    @Override
    public boolean render( Batch batch ) {
        if ( dirty ) {
            texture.load( new PixmapTextureData( pixmap, pixmap.getFormat(), false, false ) );
            texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
            dirty = false;
        }
        sprite.draw( batch );
        return false;
    }
}
