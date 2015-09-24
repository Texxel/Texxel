package com.github.texxel.tiles;

/**
 * This interface is used to declare that a tile will burn.
 */
public interface Flammable {

    /**
     * Called when fire touches the tile
     * @return true if the tile is burning and sustaining the fire.
     */
    public boolean onBurn( int x, int y );

    /**
     * Called when the fire gets extinguished.
     */
    public void onExtinguished( int x, int y );

}
