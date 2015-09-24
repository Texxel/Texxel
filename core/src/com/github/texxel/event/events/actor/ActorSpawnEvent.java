package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Actor;
import com.github.texxel.event.Cancelable;
import com.github.texxel.event.listeners.actor.ActorSpawnListener;

public class ActorSpawnEvent extends ActorEvent<ActorSpawnListener> implements Cancelable {

    private boolean cancelled = false;

    public ActorSpawnEvent( Actor actor ) {
        super(actor);
    }

    /**
     * Sets the actor that is used in this event
     * @param actor the actor to use
     */
    public void setActor( Actor actor ) {
        if ( actor == null )
            throw new NullPointerException( "actor cannot be null" );
        this.actor = actor;
    }

    @Override
    public boolean dispatch( ActorSpawnListener listener ) {
        listener.onActorSpawned( this );
        return isCancelled();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }
}