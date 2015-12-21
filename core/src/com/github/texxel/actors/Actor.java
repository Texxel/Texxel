package com.github.texxel.actors;

import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;

import java.io.Serializable;
import java.util.List;

public interface Actor extends Serializable {

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

    // TODO remove need isUserControlled function
    /**
     * Tests if the character is controlled by the user. This is only used to determine when to
     * break from the main update loop. Only the Hero should return true.
     * @return if the character is user controlled
     * @deprecated here due to an implementation detail in LevelRenderer and will be removed in the future
     */
    @Deprecated
    boolean isUserControlled();

    /**
     * Tests if this actor is covering the specific tile. If this actor has so position, then simply
     * always return false.
     * @param x the x position to test
     * @param y the y position to test
     * @return true if the actor is covering the tile
     */
    boolean isOver( int x, int y );

}