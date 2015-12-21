package com.github.texxel.actors;

import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.actor.CharDamagedListener;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.ui.Examinable;
import com.github.texxel.utils.Point2D;

public interface Char extends Actor, WorldVisual, Examinable {

    /**
     * A characters side determines how other characters react to the character.
     */
    enum Side {
        /**
         * Most mobs are evil
         */
        EVIL,
        /**
         * Neutral characters are characters like the shopkeeper
         */
        NEUTRAL,
        /**
         * Good characters are on the Hero's side
         */
        GOOD;

        public static boolean areEnemies( Side a, Side b ) {
            return ( a == EVIL && b == GOOD ) || ( a == GOOD && b == EVIL );
        }
    }

    /**
     * Gets where the character currently is standing.
     * @return the characters tile location
     * @see #setLocation(Point2D)
     */
    Point2D getLocation();

    /**
     * Sets where the character is. Generally, this shouldn't be called directly but should invoked
     * indirectly with an action. This method does only moves the character. The character will not
     * spend any time or trample any tiles. The move can be listened and altered through the
     * EventHandler returned by {@link #getMoveHandler()}
     * @param location the location to move to.
     * @return where the character was actually moved to. If the event was cancelled, then the starting
     * location will be returned.
     * @see #getLocation()
     */
    Point2D setLocation( Point2D location );

    /**
     * Launches an attack at an enemy.
     * @param enemy the enemy to attack
     * @return the damage done
     */
    float attack( Char enemy );

    /**
     * Does some damage to this char. The damage will be reduced by things such as armor or
     * resistances. The damage can be altered/cancelled through {@link #getDamageHandler()}
     * @param damage the damage to do
     * @param source the source of the damage (may be null for no source)
     * @return the damage done. 0 if it was cancelled
     */
    float damage( float damage, Object source );

    /**
     * Gets the side that this enemy is on.
     * @return the characters side
     */
    Side getSide();

    /**
     * Gets this character's FOV. The returned fov is updated as the character moves. The Level is
     * responsible for telling the FOV to update when a tile changes from being solid to opaque (or
     * vise-versa).
     * @return the character's field of vision
     */
    FieldOfVision getVision();

    @Override
    CharVisual getVisual();

    /**
     * Gets the character's current health
     * @return the chars health
     */
    float getHealth();

    /**
     * Directly sets the characters health. If the health is <= 0, then the char will be killed. If
     * the health is greater than the maximum health, then the character will be set to maximum
     * health. This method bypasses damage listeners sop careful use is advised.
     * @param health the chars new health
     */
    void setHealth( float health );

    /**
     * Gets the maximum health this character can have
     * @return the chars maximum health
     */
    float getMaxHealth();

    /**
     * Sets the characters maximum health. If the new health is smaller than the current health,
     * then the current health will be reduced, otherwise, the health will be un-effected.
     * @param health the new max health
     * @throws IllegalArgumentException if health <= 0
     */
    void setMaxHealth( float health );

    /**
     * Tests if the character's health is less than or equal to 0. If the character is dead, then
     * there should be no further interactions with the character and all references to the
     * character should be removed so the garbage collector can clean up.
     * @return true if the character is dead
     */
    boolean isDead();

    /**
     * EventHandler that fires events whenever {@link #setLocation(Point2D)} is called
     */
    EventHandler<CharMoveListener> getMoveHandler();

    /**
     * EventHandler that fires events whenever {@link #damage(float, Object)} is called
     */
    EventHandler<CharDamagedListener> getDamageHandler();

}