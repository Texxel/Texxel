package com.github.texxel.tiles;

import com.github.texxel.actors.Char;

/**
 * Characters that are smarter enough to know how to interact with tiles (only the Hero in vanilla
 * Pixel Dungeon) can use this interface to perform some fun operation on the tile.
 */
public interface Interactable extends Tile {

    /**
     * Called by an intelligent Character to interact with the tile. If the method returns false,
     * then the interaction failed and (in vanilla Pixel Dungeon) the Hero will treat the touch as
     * a standard touch
     * @param ch the character interacting with the tile
     * @return true if the interaction happened
     */
    boolean interact( Char ch, int x, int y );

}
