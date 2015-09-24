package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Actor;
import com.github.texxel.event.listeners.actor.ActorDestroyListener;

public class ActorDestroyEvent extends ActorEvent<ActorDestroyListener> {

    public ActorDestroyEvent( Actor actor ) {
        super( actor );
    }

    @Override
    public boolean dispatch( ActorDestroyListener listener ) {
        listener.onActorRemoved( this );
        return false;
    }
}
