package com.github.texxel.actors;

import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractActor implements Actor {

    private static final long serialVersionUID = 4744979006380058760L;

    private float time;
    private Goal goal;
    private final List<Sensor> sensors = new ArrayList<>();
    private final List<Sensor> unmodifiableSensors = Collections.unmodifiableList( sensors );
    private final Level level;

    /**
     * Constructs an actor who's time is at 0. Make sure to set the actor's brain as well
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
        return goal;
    }

    @Override
    public void setGoal( Goal goal ) {
        if ( goal == null )
            throw new NullPointerException( "'goal' cannot be null" );
        this.goal = goal;
    }

    @Override
    public void addSensor( Sensor sensor ) {
        sensors.add( sensor );
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

    @Override
    public boolean isUserControlled() {
        return false;
    }
}
