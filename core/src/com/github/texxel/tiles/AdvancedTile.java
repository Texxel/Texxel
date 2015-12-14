package com.github.texxel.tiles;

import com.github.texxel.levels.components.TileMap;

/**
 * Advanced tiles are not singletons like the other tiles. They know about their position in the
 * world and about the rest of the tiles
 */
public abstract class AdvancedTile extends AbstractTile {

    private static final long serialVersionUID = -6828249476402358483L;

    private final TileMap tileMap;
    private final int x, y;

    public AdvancedTile( TileMap tileMap, int x, int y ) {
        if ( tileMap == null )
            throw new NullPointerException( "'tileMap' cannot be null" );
        this.tileMap = tileMap;
        this.x = x;
        this.y = y;
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
