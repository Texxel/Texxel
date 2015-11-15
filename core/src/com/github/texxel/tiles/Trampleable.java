package com.github.texxel.tiles;

/**
 * The Trampleable interface declares that a tile may take some kind of action when walked on or
 * walked off.
 */
public interface Trampleable extends Tile {

    /**
     * Called when something walks onto the tile. The source object could be anything including
     * chars, items or a wand. In general, the source that called this method should then call the
     * {@link #onLeave(Object)} method, however, this is not required (e.g. when a mob dies in
     * a door). This method returns the tile that should replace this tile when the tile is trampled.
     * If this tile should not be replaced by anything, then it should return itself.
     * @return the tile to replace this tile with. Never null.
     */
    Tile onTrample( Object object );

    /**
     * Called when an object leaves the tile. Returns the tile to replace this tile with or itself
     * if no tile should replace it.
     * @param source the source that left (or null to not say unknown)
     * @return the tile to replace this tile. Never null
     */
    Tile onLeave( Object source );

}
