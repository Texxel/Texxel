package com.github.texxel.mechanics;

import com.github.texxel.gameloop.GameBatcher;

/**
 * The FogOfWar class is an overlay that reduces the users visibility of the world. The fog has a
 * co-ordinate system that is offset by half a cell from the level that it is overlaying. This is to
 * ensure that tiles that are behind solid tiles cannot be seen
 */
public interface FogOfWar extends GameBatcher.OptimisedDrawer {

    /**
     * The amount of cells vertically in the fog
     * @return the width
     */
    int width();

    /**
     * The amount of cells horizontally in the fog
     * @return the height
     */
    int height();

    /**
     * Sets the color of a specific square in the fog. The passed co-ordinates are in the fog's
     * co-ordinate system which is offset from the levels co-ordinates by half a cell
     * @param x the x co-ord
     * @param y the y co-ord
     * @param color the color in RGBA8888 encoding
     * @throws IndexOutOfBoundsException if x or y is out of bounds
     */
    void setColor( int x, int y, int color );

    /**
     * Gets the color of a specific square of the fog
     * @param x the x cell to get
     * @param y the y cell to get
     * @return the cells color in RGBA8888 encoding
     * @throws IndexOutOfBoundsException if x or y is out of bounds
     */
    int getColor( int x, int y );

    /**
     * Sets the color of the four squares surrounding a tile to a single color. The co-ordinates are
     * in the levels co-ordinates system
     * @param x the tile's x position
     * @param y the tile's y position
     * @param color the color in RGBA8888 encoding
     * @throws IndexOutOfBoundsException if x or y is out of bounds
     */
    void setColorAroundTile( int x, int y, int color );

}
