package com.github.texxel.event.events.actor;

import com.github.texxel.actors.Char;
import com.github.texxel.event.Cancelable;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.utils.Point2D;

/**
 * The CharMoveEvent is called whenever a character moves for any reason. It also forms an base for
 * the other move events to extend from
 */
public class CharMoveEvent extends CharEvent<CharMoveListener> implements Cancelable {

    private Point2D to;
    private Point2D from;
    private boolean cancelled;

    /**
     * Constructs a move event that will decide where the character moves to.
     * @param character the character to move
     * @param from the point moving from (generally should be same as character.getLocation()
     * @param to the point to move to
     * @throws NullPointerException if any of the parameters are null
     */
    public CharMoveEvent( Char character, Point2D from, Point2D to ) {
        super( character );
        if ( from == null )
            throw new NullPointerException( "from cannot be null" );
        if ( to == null )
            throw new NullPointerException( "to cannot be null" );
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the point to go to
     * @return the next move
     */
    public Point2D getTo() {
        return to;
    }

    /**
     * Sets the point to go to
     * @param to the point to go to
     */
    public void setTo( Point2D to ) {
        if ( to == null )
            throw new NullPointerException( "to cannot be null" );
        this.to = to;
    }

    /**
     * Gets the point that the character has moved from. This is generally the same as {@code
     * character.getLocation()}
     * @return the point moved from
     */
    public Point2D getFrom() {
        return from;
    }

    @Override
    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean dispatch( CharMoveListener listener ) {
        listener.onCharMove( this );
        return cancelled;
    }
}
