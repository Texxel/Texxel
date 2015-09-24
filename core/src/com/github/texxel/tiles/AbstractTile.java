package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

public abstract class AbstractTile implements Tile {

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        return topLevel.newBundle();
    }

    @Override
    public void restore( Bundle bundle ) {
    }

    @Override
    public Animation getLogo() {
        return new Animation( 1.0f, getImage() );
    }

    @Override
    public boolean isOver( int x, int y ) {
        throw new UnsupportedOperationException( "tiles don't know where they are" );
    }
}
