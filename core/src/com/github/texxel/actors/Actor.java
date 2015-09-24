package com.github.texxel.actors;

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

    com.github.texxel.actors.ai.Action getAction();

    void setNextAction( com.github.texxel.actors.ai.Action action );

    /**
     * Tests if the character is controlled by the user. This is only used to determine when to
     * break from the main update loop. Only the Hero should return true.
     * @return if the character is user controlled
     */
    boolean isUserControlled();
    
}