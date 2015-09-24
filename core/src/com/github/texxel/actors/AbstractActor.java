package com.github.texxel.actors;

import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

public abstract class AbstractActor implements Actor {

    private float time;
    private com.github.texxel.actors.ai.Action nextAction;

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
        // But sub classes should still call this method in
        // case there is something to do in the future
    }

    /**
     * Constructs an actor who's time is at 0
     */
    public AbstractActor() {
        time = 0;
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
    public com.github.texxel.actors.ai.Action getAction() {
        com.github.texxel.actors.ai.Action action = nextAction;
        nextAction = null;
        return action;
    }

    @Override
    public void setNextAction( com.github.texxel.actors.ai.Action action ) {
        this.nextAction = action;
    }

    @Override
    public boolean isUserControlled() {
        return false;
    }
}
