package com.github.texxel.levels.components;

import com.github.texxel.event.Event;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.level.TileSetListener;
import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.tiles.Tile;

public class TileMap implements Bundlable {

    static {
        ConstructorRegistry.put( TileMap.class, new Constructor<TileMap>() {

            @Override
            public TileMap newInstance( Bundle bundle ) {
                final int width = bundle.getInt( "width" );
                final int height = bundle.getInt( "height" );
                return new TileMap( width, height );
            }
        } );
    }

    private final Tile[][] tiles;
    private final int width, height;
    private final boolean[][] losBlocking;
    private final boolean[][] passables;
    private final boolean[][] spawnables;
    private final TileSetEvent tileSetEvent = new TileSetEvent();
    private final EventHandler<TileSetListener> tileSetHandler = new EventHandler<>();

    public TileMap( final int width, final int height ) {
        if ( width <= 0 || height <= 0 )
            throw new IllegalArgumentException( "Width or height cannot be <= 0. Passed width="
                    + width + " height=" + height );
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        this.losBlocking = new boolean[width][height];
        this.passables = new boolean[width][height];
        this.spawnables = new boolean[width][height];
    }

    /**
     * Constructs a TileMap of a specific size and fills in the tiles
     * @param width the tile maps width
     * @param height the tile maps height
     * @param filler the FullFiller to use to fill the map
     * @throws IllegalArgumentException if width or height <= 0
     * @throws NullPointerException if filler is null
     */
    public TileMap( final int width, final int height, TileFiller.FullFiller filler ) {
        this( width, height );
        filler.paint( this );
    }

    /**
     * Gets the tile at a specific location
     * @param x the x position
     * @param y the y position
     * @return the tile
     * @throws IndexOutOfBoundsException if the x or y positions are not within the tiles bounds
     */
    public Tile getTile( int x, int y ) {
        return tiles[x][y];
    }

    public void setSpawnable( int x, int y, boolean spawnable ) {
        spawnables[x][y] = spawnable;
    }

    public boolean isSpawnable( int x, int y ) {
        return spawnables[x][y];
    }

    /**
     * @return the tile maps width
     */
    public int width() {
        return width;
    }

    /**
     * @return the tile maps height
     */
    public int height() {
        return height;
    }

    /**
     * Gets a cached array of the tiles that block a character's line of sight. Note that the
     * returned array is not a copy, thus, altering the returned array may result in some strange
     * results. It is advised that it never be done.
     * @return the lineOfSight blocking tiles
     */
    public boolean[][] getLosBlocking() {
        return losBlocking;
    }

    /**
     * Gets a cached array of the passable tiles. Changes to the returned array are persistent and
     * may cause subtle bugs in other programs. Do not edit the returned array; unless you really
     * know what you're doing ;)
     * @return the passables tiles (true marks passable)
     */
    public boolean[][] getPassables() {
        return passables;
    }

    /**
     * Sets the tile at the specific position
     * @param x the tiles x position
     * @param y the tiles y position
     * @param tile the tile to set
     * @return true if the tile was set. false if a plugin canceled the event
     * @throws NullPointerException if the tile is null
     * @throws IndexOutOfBoundsException if the location is out of bounds
     */
    public boolean setTile( int x, int y, Tile tile ) {
        if ( tile == null )
            throw new NullPointerException( "tile cannot be null" );
        if ( x < 0 || x >= width || y < 0 || y >= height )
            throw new IndexOutOfBoundsException( "(x,y) is out of bounds. Passed: x=" + x + " y=" + y );
        TileSetEvent tileSetEvent = this.tileSetEvent;
        tileSetEvent.tile = tile;
        tileSetEvent.x = x;
        tileSetEvent.y = y;
        if ( tileSetHandler.dispatch( tileSetEvent ) )
            return false;
        tiles[x][y] = tile;
        losBlocking[x][y] = tile.isOpaque();
        passables[x][y] = tile.isPassable();
        spawnables[x][y] = !tile.isSolid() && tile.isPassable();
        return true;
    }

    @Override
    public Bundle bundle( BundleGroup bundleGroup ) {
        Bundle bundle = bundleGroup.newBundle();
        bundle.put( "width", width );
        bundle.put( "height", height );
        Tile[][] tiles = this.tiles;
        for ( int i = 0; i < width; i++ ) {
            for ( int j = 0; j < height; j++ ) {
                bundle.put( "t:" + i + ":" + j, tiles[i][j] );
                bundle.put( "s:" + i + ":" + j, spawnables[i][j] );
            }
        }
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        for ( int i = 0; i < width; i++ ) {
            for ( int j = 0; j < height; j++ ) {
                Tile tile = bundle.getBundlable( "t:" + i + ":" + j );
                tiles[i][j] = tile;
                losBlocking[i][j] = tile.isOpaque();
                passables[i][j] = tile.isPassable();
                spawnables[i][j] = bundle.getBoolean( "s:" + i + ":" + j );
            }
        }
    }

    /**
     * Gets the handler that fire events when a tile is changed
     */
    public EventHandler<TileSetListener> getTileSetHandler() {
        return tileSetHandler;
    }

    private class TileSetEvent implements Event<TileSetListener> {
        Tile tile;
        int x, y;

        @Override
        public boolean dispatch( TileSetListener listener ) {
            return listener.onTileSet( TileMap.this, tile, x, y );
        }
    }

}
