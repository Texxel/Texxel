package com.github.texxel.actors;

import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.sprites.api.CharVisual;

/**
 * A Breed defines what a character is. Breeds should be immutable so they can be shared between
 * different characters. All the methods in Breed define the <em>starting</em> behaviour for a
 * character. All the values can be altered afterwards.
 */
public interface Breed {

    Goal goal( Char c );

    Sensor[] sensors( Char c );

    /**
     * Gets the side that this enemy is on.
     * @return the characters side
     */
    Char.Side side( Char c );

    /**
     * Gets what this character looks like
     * @return the character's visual
     */
    CharVisual visual( Char c );

    /**
     * Gets the character's current health
     * @return the chars health
     */
    float maxHealth( Char c );

}
