package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Char;
import com.github.texxel.event.Cancelable;
import com.github.texxel.event.listeners.actor.CharDamagedListener;

public class CharDamagedEvent extends CharEvent<CharDamagedListener> implements Cancelable {

    private final Object source;
    private float damage;
    private boolean cancelled = false;

    /**
     * Constructs a damage event
     * @param c the character that was damaged
     * @param source the source of the damage (may be null to not specify)
     * @param damage the damage done (negative damage will most likely heal. Generally should be avoided)
     */
    public CharDamagedEvent( Char c, Object source, float damage ) {
        super( c );
        this.source = source;
        this.damage = damage;
    }

    @Override
    public boolean dispatch( CharDamagedListener listener ) {
        listener.onCharDamaged( this );
        return cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the damage that will be done to this character unless altered
     * @return the damage done
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Sets the damage to do to the character
     * @param damage the damage to do to the character
     */
    public void setDamage( float damage ) {
        this.damage = damage;
    }

    /**
     * The source of the damage
     * @return the damage source (may be null)
     */
    public Object getSource() {
        return source;
    }

}
