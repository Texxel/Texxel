package com.github.texxel.event.listeners.level;

import com.github.texxel.event.Listener;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.tiles.Tile;

public interface TileSetListener extends Listener {

    /**
     * Called when a tile is about to be set
     * @param tileMap the TileMap that changed
     * @param tile the tile that cell will be updated to
     * @param x the x position
     * @param y the y position
     * @return true to cancel the event, false to continue with the event
     */
    boolean onTileSet( TileMap tileMap, Tile tile, int x, int y );

}
