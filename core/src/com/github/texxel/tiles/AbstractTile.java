package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Pre defines the methods that will be the same for all tiles.
 */
public abstract class AbstractTile implements Tile {

    private static final long serialVersionUID = 9063335581952836068L;

    @Override
    public Animation getLogo() {
        return new Animation( 1.0f, getDefaultImage() );
    }

    @Override
    public boolean isOver( int x, int y ) {
        throw new UnsupportedOperationException( "tiles don't know where they are" );
    }
}
