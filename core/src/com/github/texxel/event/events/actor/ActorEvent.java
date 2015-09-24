package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Actor;
import com.github.texxel.event.Event;
import com.github.texxel.event.Listener;

/**
 * base class for all actor events
 * @param <T>
 */
public abstract class ActorEvent <T extends Listener> implements Event<T> {

    /**
     * The actor that will be returned in {@link #getActor()}
     */
    protected Actor actor;

    /**
     * Constructs the actor spawn event with an actor
     * @param actor the actor of interest
     */
    public ActorEvent( Actor actor ) {
        if ( actor == null )
            throw new NullPointerException( "actor cannot be null" );
        this.actor = actor;
    }

    /**
     * Gets the actor of the event
     * @return the actor
     */
    public Actor getActor() {
        return actor;
    }

}
