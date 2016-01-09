package com.github.texxel.actors;

import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;

import java.io.Serializable;
import java.util.List;

public interface Actor extends Serializable {

    // TODO actors should not have a level
    // all stuff needing to know about the level should be done in the AI
    /**
     * Gets the level the actor is on
     * @return the current level
     */
    Level level();

    /**
     * Sets the level this actor is in. This should be used sparingly as implementations might
     * require recreating many objects when this is called.
     * @param level the new level to go to
     */
    void setLevel( Level level );

    /**
     * Sets back the next actions by an amount of turns
     * @param time the time to wait in turns
     */
    void spend( float time );

    /**
     * Charges up this actors energy by a little bit. By default, this restores 1 energy. Subclasses
     * may override this to do as they feel
     */
    void charge();

    /**
     * Gets how much energy this actor has
     * @return this actors energy
     */
    float getEnergy();

    /**
     * Sets the energy that this actor has
     * @param energy the actors new energy
     */
    void setEnergy( float energy );

    /**
     * Gets this actors goal.
     * @return the current goal. Never null
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

    /**
     * Tests if this actor is covering the specific tile. If this actor has so position, then simply
     * always return false.
     * @param x the x position to test
     * @param y the y position to test
     * @return true if the actor is covering the tile
     */
    boolean isOver( int x, int y );

}