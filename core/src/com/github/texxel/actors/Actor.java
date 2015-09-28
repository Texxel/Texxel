package com.github.texxel.actors;

import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.saving.Bundlable;

public interface Actor extends Bundlable {

    /**
     * Sets back the next actions by an amount of turns
     * @param time the time to wait in turns
     */
    void spend( float time );

    /**
     * Gets the time that the actor will next do something at
     * @return the time for the actors next turn
     */
    float getTime();

    /**
     * Sets the time at which the actor will do his next action
     * @param time the time to act
     */
    void setTime( float time );

    /**
     * Gets this actors goal
     * @return the current goal. Should never be null
     */
    Goal getGoal();

    /**
     * Sets what the actor should be doing
     * @param goal the actors current goal
     * @throws NullPointerException if goal is null
     */
    void setGoal( Goal goal );

    /**
     * Gets the brain of this actor
     * @return the actors brain
     */
    Brain getBrain();

    /**
     * Sets this actors AI
     * @param brain the actors ai
     */
    void setBrain( Brain brain );

    /**
     * Tests if the character is controlled by the user. This is only used to determine when to
     * break from the main update loop. Only the Hero should return true.
     * TODO remove need isUserControlled function
     * @return if the character is user controlled
     */
    boolean isUserControlled();

}