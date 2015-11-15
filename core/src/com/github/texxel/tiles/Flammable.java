package com.github.texxel.tiles;

/**
 * This interface is used to declare that a tile will burn.
 */
public interface Flammable extends Tile {

    /**
     * Called when fire touches the tile
     * @return true if the tile is burning and sustaining the fire.
     */
    boolean onBurn();

    /**
     * Gets the amount of turns that this tile will burn for
     * @return the tile's burn time
     */
    int burnTime();

    /**
     * Called when the fire gets extinguished. Before this method is called, the tilemap will set
     * the current cell to an embers tile. If this is not the desired behaviour, then this method is
     * a good place to change that behaviour.
     */
    void onExtinguished();

}
