package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Char;
import com.github.texxel.event.Listener;

public abstract class CharEvent<T extends Listener> extends ActorEvent<T> {

    protected Char character;

    public CharEvent( Char character ) {
        super( character );
        this.character = character;
    }

    @Override
    public Char getActor() {
        return (Char)super.getActor();
    }
}
