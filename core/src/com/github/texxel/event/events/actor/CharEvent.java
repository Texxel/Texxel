package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Char;
import com.github.texxel.event.Listener;

public abstract class CharEvent<T extends Listener> extends ActorEvent<T> {

    public CharEvent( Char character ) {
        super( character );
    }

    @Override
    public Char getActor() {
        return (Char)super.getActor();
    }

    /**
     * The exact same as {@link #getActor()} but with a better name
     * @return the character of this event
     */
    public Char getCharacter() {
        return (Char)super.getActor();
    }
}
