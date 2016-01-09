package com.github.texxel.actors;

import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;
import com.github.texxel.utils.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractActor implements Actor {

    private static final long serialVersionUID = 4744979006380058760L;

    private float time;
    private Goal goal;
    private final List<Sensor> sensors = new ArrayList<>();
    private final List<Sensor> unmodifiableSensors = Collections.unmodifiableList( sensors );
    private Level level;

    /**
     * Constructs an actor who's time is at 0
     */
    public AbstractActor( Level level ) {
        time = 0;
        this.level = level;
    }

    @Override
    public Level level() {
        return level;
    }

    @Override
    public void setLevel( Level level ) {
        this.level = Assert.nonnull( level, "level cannot be null" );
        // revert back to the default goal (since all context of the previous goal will be lost)
        this.goal = null;
    }

    @Override
    public void spend( float energy ) {
        this.time += energy;
    }

    @Override
    public float getTime() {
        return time;
    }

    @Override
    public void setTime( float energy ) {
        this.time = energy;
    }

    @Override
    public Goal getGoal() {
        if ( goal == null )
            goal = Assert.nonnull( defaultGoal(), getClass() + " returned a null default goal" );
        return goal;
    }

    /**
     * Gets the goal that this actor should go back to when there is no goal set. In Texxel, this
     * is only used for setting an initial goal and setting a new goal when the actor moves from
     * one depth to the next
     * @return a default goal
     */
    protected abstract Goal defaultGoal();

    @Override
    public void setGoal( Goal goal ) {
        if ( goal == null )
            throw new NullPointerException( "'goal' cannot be null" );
        this.goal = goal;
    }

    @Override
    public void addSensor( Sensor sensor ) {
        if ( sensor == null )
            throw new NullPointerException( "'sensor' cannot be null" );
        sensors.add( sensor );
        sensor.onStart();
    }

    @Override
    public List<Sensor> getSensors() {
        return unmodifiableSensors;
    }

    @Override
    public void remove( Sensor sensor ) {
        //noinspection StatementWithEmptyBody
        while ( sensors.remove( sensor ) ) {
        }
        sensor.onRemove();
    }
}
