package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Char;
import com.github.texxel.event.Cancelable;
import com.github.texxel.event.listeners.actor.CharHealthChangedListener;

public class CharHealthChangedEvent extends CharEvent<CharHealthChangedListener> implements Cancelable {

    private boolean cancelled = false;
    private float next;
    private float previous;

    public CharHealthChangedEvent( Char character, float previousHealth, float nextHealth ) {
        super( character );
        previous = previousHealth;
        next = nextHealth;
    }

    @Override
    public boolean dispatch( CharHealthChangedListener listener ) {
        listener.onHealthChanged( this );
        return cancelled;
    }

    /**
     * Gets the health that the character had before this event was fired
     * @return the characters previous health
     */
    public float getPreviousHealth() {
        return previous;
    }

    /**
     * Gets the health the player is planned to have
     * @return the player's next health
     */
    public float getNextHealth() {
        return next;
    }

    /**
     * Sets the health that the player will get
     * @param next the players next health
     * @return this
     */
    public CharHealthChangedEvent setNextHealth( float next ) {
        this.next = next;
        return this;
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
