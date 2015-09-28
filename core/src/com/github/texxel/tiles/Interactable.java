package com.github.texxel.tiles;

import com.github.texxel.actors.Char;

/**
 * Characters that are smarter enough to know how to interact with tiles (only the Hero in vanilla
 * Pixel Dungeon) can use this interface to perform some fun operation on the tile.
 */
public interface Interactable extends Tile {

    /**
     * Called by an intelligent Character to interact with the tile. Before calling this method,
     * {@link #canInteract(Char, int, int)} should always be called to make sure that the tile
     * can be interacted with.
     * @param ch the character interacting with the tile
     */
    void interact( Char ch, int x, int y );

    /**
     * Tests if the character can interact with the tile. This method should also check that the
     * character is close enough to interact with the tile.
     * @param ch the character to test an interaction with.
     * @param x the tiles x position
     * @param y the tiles y position
     * @return true if the character can interact with the tile
     */
    boolean canInteract( Char ch, int x, int y );

}
