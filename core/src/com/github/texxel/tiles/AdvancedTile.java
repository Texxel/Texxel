package com.github.texxel.tiles;

import com.github.texxel.levels.components.TileMap;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

/**
 * Advanced tiles are not singletons like the other tiles. They know about their position in the
 * world and about the rest of the tiles
 */
public abstract class AdvancedTile extends AbstractTile {

    private TileMap tileMap;
    private final int x, y;

    public AdvancedTile( TileMap tileMap, int x, int y ) {
        if ( tileMap == null )
            throw new NullPointerException( "'tileMap' cannot be null" );
        this.tileMap = tileMap;
        this.x = x;
        this.y = y;
    }

    protected AdvancedTile( Bundle bundle ) {
        this.x = bundle.getInt( "x" );
        this.y = bundle.getInt( "y" );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = super.bundle( topLevel );
        bundle.put( "x", x );
        bundle.put( "y", y );
        bundle.put( "tileMap", tileMap );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        super.restore( bundle );
        tileMap = bundle.getBundlable( "tileMap" );
    }

    protected TileMap tileMap() {
        return tileMap;
    }

    protected int x() {
        return x;
    }

    protected int y() {
        return y;
    }
}
