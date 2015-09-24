package com.github.texxel.event;

/**
 * An Event is designed to inform many listeners that something has happened. To assist with
 * event dispatching, the {@link EventHandler} can be very helpful
 * @param <E> the type of listener that can listen to this event
 */
public interface Event<E extends Listener> {

    /**
     * Dispatches an event to a listener. This method is called once for each registered listener
     * per event fire. If the event gets cancelled (by returning true) then no listeners of lower
     * priority will be informed of the event
     * @param listener the listener to call
     * @return true to cancel the event. false to continue.
     */
    boolean dispatch( E listener );

}
