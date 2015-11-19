package com.github.texxel.actors;

import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractActor implements Actor {

    private float time;
    private Brain brain;
    private Goal goal;
    private List<Sensor> sensors = new ArrayList<>();
    private List<Sensor> unmodifiableSensors = Collections.unmodifiableList( sensors );
    private Level level;

    /**
     * Constructs an actor who's time is at 0. Make sure to set the actor's brain as well
     */
    public AbstractActor( Level level ) {
        time = 0;
        this.level = level;
    }

    /**
     * Constructs the actor from a bundle
     * @param bundle the bundle to restore from
     */
    protected AbstractActor( Bundle bundle ) {
        if ( !bundle.contains( "time" ) )
            throw new IllegalArgumentException( "bundle must have 'time' mapping" );
        this.time = (float)bundle.getDouble( "time" );
    }

    @Override
    public Level level() {
        return level;
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putDouble( "time", time );
        bundle.putBundlable( "level", level );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        level = bundle.getBundlable( "level" );
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
    public Brain getBrain() {
        return brain;
    }

    @Override
    public void setBrain( Brain brain ) {
        if ( brain == null )
            throw new NullPointerException( "'brain' cannot be null" );
        this.brain = brain;
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
