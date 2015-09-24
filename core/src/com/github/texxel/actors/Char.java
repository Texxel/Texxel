package com.github.texxel.actors;

import com.github.texxel.actors.ai.State;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.CharTargetEvent;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.event.listeners.actor.CharTargetListener;
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
         * Neutral characters are characters like the shop keeper
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
     * @return where the character was actually moved to (as it might have been altered by a plugin)
     * or null if the move event was cancelled.
     * @see #getLocation()
     */
    Point2D setLocation( Point2D location );

    /**
     * Gets where this character is trying to go to. If the character is not trying to go anywhere,
     * then null will be returned.
     * @apiNote having a target set does <i>not</i> always mean that the character actually has a
     * target: the returned target may just be some place that the character is wandering aimlessly
     * towards.
     * // TODO add target priorities
     * @return the current target
     */
    Point2D getTarget();

    /**
     * Tells the character that it should try to go towards a specific goal. If the character sees
     * something else that it wants to do along the way, it will path towards the goal. Plugins
     * can alter where a character targets though a {@link CharTargetEvent}.
     * @param target where to try to get to
     * @return true if the target was selected
     * @see #getTargetHandler()
     */
    boolean target( Point2D target );

    /**
     * Launches an attack at an enemy.
     * @param enemy the enemy to attack
     */
    void attack( Char enemy );

    /**
     * Sets the State that this char is in.
     * @param state the state to use
     * @throws NullPointerException if the state is null
     */
    void setState( State state );

    /**
     * Gets the state that the character is in
     * @return the characters current state
     */
    State getState();

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
     * EventHandler that fires events whenever {@link #target(Point2D)} is called
     */
    EventHandler<CharTargetListener> getTargetHandler();

    /**
     * EventHandler that fires events whenever {@link #setLocation(Point2D)} is called
     */
    EventHandler<CharMoveListener> getMoveHandler();


}