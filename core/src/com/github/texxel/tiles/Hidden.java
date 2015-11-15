package com.github.texxel.tiles;

import com.github.texxel.actors.Char;

/**
 * Declares that this tile hides something that is not immediately obvious. When a character
 * approaches the tile. they will have a chance of seeing the hidden item. The chance is dependent
 * on the character's awareness and distance to the tile.
 */
public interface Hidden {

    /**
     * Nothing will ever see the thing
     */
    int INVISIBLE = 0;
    /**
     * The tile is quite hard to see
     */
    int DISGUISED = 25;
    /**
     * The tile is neither hard nor easy to see
     */
    int VAGUE     = 50;
    /**
     * The tile is fairly easy to see
     */
    int BLURRY    = 75;
    /**
     * The tile will be seen immediately
     */
    int OBVIOUS   = 100;

    /**
     * Gets how obvious the tile is. The returned integer should be a number between 0 and 100; 0 is
     * impossible to be seen, 100 is dead obvious. A number in the middle means that only some
     * characters will be able to see it. Generally, one of the defaults should be returned:
     * <ul>
     *     <li>{@link Hidden#INVISIBLE}</li>
     *     <li>{@link Hidden#DISGUISED}</li>
     *     <li>{@link Hidden#VAGUE}</li>
     *     <li>{@link Hidden#BLURRY}</li>
     *     <li>{@link Hidden#OBVIOUS}</li>
     * </ul>
     * @return the tile's obviousness
     */
    int visibility();

    /**
     * Called when the given character sees the tile
     * @param character the character that saw the tile
     */
    void onSeen( Char character );

}
