package com.github.texxel.actors;

import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundlable;

import java.util.List;

public interface Actor extends Bundlable {

    /**
     * Gets the level the actor is on
     * @return the current level
     */
    Level level();

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
     * Adds a sensor to the actor
     * @param sensor the sensor to add
     * @throws NullPointerException if sensor is null
     */
    void addSensor( Sensor sensor );

    /**
     * Gets an un-modifiable list of all the sensors attached to this object
     * @return this actors sensors
     */
    List<Sensor> getSensors();

    /**
     * Removes a sensor from the actor
     * @param sensor the sensor to remove
     */
    void remove( Sensor sensor );

    /**
     * Tests if the character is controlled by the user. This is only used to determine when to
     * break from the main update loop. Only the Hero should return true.
     * TODO remove need isUserControlled function
     * @return if the character is user controlled
     */
    boolean isUserControlled();

}