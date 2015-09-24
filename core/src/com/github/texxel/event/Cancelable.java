package com.github.texxel.event;

/**
 * An interface to advertise that an event can be cancelled. Events that get cancelled should do
 * absolutely nothing. Furthermore, is an event implements Cancelable, listeners should check if the
 * event has been cancelled and return immediately if it has been.
 */
public interface Cancelable {

    /**
     * Tests if the event has been cancelled. If the event is cancelled, no actions should be taken
     * based on this event
     * @return true if the event has been cancelled
     */
    public boolean isCancelled();

    /**
     * Sets the event to be cancelled. It is possible to un-cancel an already cancelled event, but
     * it should be rare to find a use for this function.
     * @param cancelled true to cancel the event
     */
    public void setCancelled( boolean cancelled );
}
