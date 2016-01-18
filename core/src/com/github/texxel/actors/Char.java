package com.github.texxel.actors;

import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.actor.CharAttackListener;
import com.github.texxel.event.listeners.actor.CharHealthChangedListener;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.attacking.Attack;
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
     * Gets the attribute with the given name as an id. If the attribute does not exist, then it
     * will be created with a base value of 0.
     * @param name the name of the attribute
     * @return the attribute
     */
    Attribute getAttribute( String name );

    /**
     * Tests if this char has an attribute
     * @param name the name of the attribute to test for
     * @return true if the attribute exists
     */
    boolean hasAttribute( String name );


    void damage( Attack attack );

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
     * health. The set health can be altered though {@link #getHealthHandler()}
     * @param health the chars new health
     * @return the characters new health (always what was passed to the event except when the event
     * gets altered by plugins)
     */
    float setHealth( float health );

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

    EventHandler<CharAttackListener> getAttackHandler();

    /**
     * EventHandler that is dispatched every time {@link #setHealth(float)} is called
     */
    EventHandler<CharHealthChangedListener> getHealthHandler();

    /**
     * Gets the default weapon used by this character. In the case of mobs, this is almost always
     * just a dummy object used to represent a hand attack. In the case of the hero, this the equipped
     * weapon or the hand weapon if no weapon is equipped.
     * @return the weapon to use
     */
    Weapon weapon();

}