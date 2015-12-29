package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Action;

/**
 * The NoBrainAction if for actors that never do anything. The NoBrainAction will simply tell the
 * actor to spend some more of its time.
 */
public class NoBrainAction implements Action {

    private final Actor actor;

    public NoBrainAction( Actor actor ) {
        if ( actor == null )
            throw new NullPointerException( "'actor' cannot be null" );
        this.actor = actor;
    }

    @Override
    public void onStart() {
        actor.spend( 1 );
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public boolean render() {
        return true;
    }

    @Override
    public void forceFinish() {

    }

    @Override
    public void onFinish() {

    }
}
