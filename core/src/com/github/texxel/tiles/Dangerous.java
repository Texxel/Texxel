package com.github.texxel.tiles;

import com.github.texxel.actors.Char;

/**
 * This interface is used to declare that a tile should not be walked on. Note that this interface
 * is not needed if the tile is solid
 */
public interface Dangerous {

    /**
     * Tests if the given character should try to path around the tile
     * @param ch the Character who is asking if they should avoid the tile
     * @return true if the character should path around the tile
     */
    boolean shouldAvoid( Char ch );

}
