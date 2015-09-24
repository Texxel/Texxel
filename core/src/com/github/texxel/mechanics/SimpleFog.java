package com.github.texxel.mechanics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A simple implementation of the FogOfWar interface
 */
public class SimpleFog implements FogOfWar {

    final int width, height;
    final int textureWidth, textureHeight;
    final Pixmap pixmap;
    final Sprite sprite;
    Texture texture;
    private boolean dirty = false;

    public SimpleFog( int width, int height ) {
        if ( width <= 0 || height <= 0 )
            throw new IllegalArgumentException( "width and height must be > 0. Passed width="
                    + width + " height=" + height );
        width++;
        height++;
        this.width = width;
        this.height = height;

        int textureWidth = 2;
        int textureHeight = 2;
        while ( textureWidth < width )
            textureWidth <<= 1;
        while ( textureHeight < height )
            textureHeight <<= 1;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        pixmap = new Pixmap( textureWidth, textureHeight, Pixmap.Format.RGBA8888 );
        Pixmap.setBlending( Pixmap.Blending.None );
        pixmap.setColor( Color.BLACK );
        pixmap.fill();

        sprite = new Sprite( texture = new Texture( pixmap ));
        sprite.setPosition( -0.5f, -0.5f );
        //sprite.setSize( textureWidth, height );
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
        if ( 0 > x || x >= width || y < 0 || width <= y )
            throw new IndexOutOfBoundsException( "x or y was out of bounds. Passed x="+ x + " y=" + y );
        pixmap.drawPixel( x, textureHeight - y - 1, color );
        dirty = true;
    }

    @Override
    public int getColor( int x, int y ) {
        return pixmap.getPixel( x, y );
    }

    @Override
    public void setColorAroundTile( int x, int y, int color ) {
        if ( x < 0 || x >= width-1 || y < 0 || y >= height-1 )
            throw new IndexOutOfBoundsException( "x or y out of bounds. Passed x=" + x + " y=" + y
                    +". width=" + width + " height=" + height );
        pixmap.setColor( color );
        pixmap.drawRectangle( x, textureHeight - y - 2, 2, 2 );
        dirty = true;
    }

    @Override
    public void onDraw( SpriteBatch batch ) {
        // FIXME very inefficient fog drawing
        if ( dirty ) {
            texture.dispose();
            texture = new Texture( pixmap );
            texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
            sprite.setTexture( texture );
            dirty = false;
        }
        sprite.draw( batch );
    }
}
