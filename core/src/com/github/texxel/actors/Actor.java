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
     * Clones the actor to a new level. This method is used to make actors transverse levels. This
     * method does not remove the actor from the current level nor add the actor to the next level -
     * that is the responsibility of the caller (if appropriate).
     *
     * Even if the actor is not expected to cross level boundaries (like all mobs or heaps), a mod
     * may want the actor to move across, thus, this method should always be carefully implemented.
     * @param level the level to move to
     * @return an exact clone of this actor but in the other level
     */
    Actor cloneTo( Level level );

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