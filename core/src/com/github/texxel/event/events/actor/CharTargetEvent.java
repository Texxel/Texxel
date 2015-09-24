package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Char;
import com.github.texxel.event.Cancelable;
import com.github.texxel.event.listeners.actor.CharTargetListener;
import com.github.texxel.utils.Point2D;

/**
 * The CharTargetEvent is called whenever a character changes its target.
 */
public class CharTargetEvent extends CharEvent<CharTargetListener> implements Cancelable {

    private Point2D target;
    private boolean cancelled = false;

    /**
     * Constructs a char targeting event. The character will (by default) try to go to {@code target}.
     * If target is null, then the character's target will be removed.
     * @param character the character to control
     * @param target the point to go to (or null to cancel target)
     */
    public CharTargetEvent( Char character, Point2D target ) {
        super( character );
        this.target = target;
    }

    /**
     * Gets the location that is being targeted or null if no target is selected
     * @return the chars next target
     */
    public Point2D getTarget() {
        return target;
    }

    /**
     * Sets what point to target. Null will remove the characters target
     * @param target the target to go to
     */
    public void setTarget( Point2D target ) {
        this.target = target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean dispatch( CharTargetListener listener ) {
        listener.onCharTarget( this );
        return isCancelled();
    }
}
