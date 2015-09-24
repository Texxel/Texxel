package com.github.texxel.tiles;

/**
 * The Trampleable interface declares that a tile may take some kind of action when walked on or
 * walked off.
 */
public interface Trampleable {

    /**
     * Called when something walks onto the tile. The source object could be anything including
     * chars, items or a wand. In general, the source that called this method should then call the
     * {@link #onLeave(Object, int, int)} method, however, this is not required (e.g. when a mob dies in
     * a door)
     * @param source the object that trampled the tile. May be null
     * @param x the x pos
     * @param y the y pos
     */
    public void onTrample( Object source, int x, int y );

    /**
     * Called when an object leaves the tile
     * @param source the source that left (or null to not say)
     * @param x the x pos
     * @param y the y pos
     */
    public void onLeave( Object source, int x, int y );

}
