package com.github.texxel.actors;

import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

public abstract class AbstractActor implements Actor {

    private float time;
    private Brain brain;
    private Goal goal;

    /**
     * Constructs an actor who's time is at 0. Make sure to set the actor's brain as well
     */
    public AbstractActor() {
        time = 0;
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
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.put( "time", time );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        // nothing to do.
        // But sub classes should still call this method in case there is something
        // to do in the future
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
    public boolean isUserControlled() {
        return false;
    }
}
