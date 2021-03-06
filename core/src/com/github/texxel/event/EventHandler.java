package com.github.texxel.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * The EventHandler assists with dispatching events to a set of listeners.
 * @param <L> the type of listener that can be added
 */
public final class EventHandler<L extends Listener> implements Serializable {

    private static final long serialVersionUID = 4177507508521495393L;

    /**
     * Priority to use for making big changes to an event.
     */
    public static final int VERY_EARLY = 1000;
    /**
     * Priority used by Texxel to alter events. Plugins should not using this priority
     */
    public static final int TEXXEL_ALTER = 500;
    /**
     * Priority to use for small changes to an event
     */
    public static final int EARLY = 100;
    /**
     * A mid way priority. This priority should rarely be used.
     */
    public static final int NORMAL = 0;
    /**
     * Priority for listening to events. Don't make any changes on this priority
     */
    public static final int LATE = -100;
    /**
     * Priority used by Texxel to listen into events. Plugins should avoid using this priority
     */
    public static final int TEXXEL_LISTEN = -500;
    /**
     * Priority for listening to events. You should never use this priority except for when debugging.
     */
    public static final int VERY_LATE = -1000;

    // The full list of all the listeners mapped to the priority being listened to
    TreeMap<Integer, HashSet<Listener>> listeners = new TreeMap<>();

    // Iterating over the tree map is slow! so we bake the listeners order into an array
    transient Listener[] listenersBaked = null;

    /**
     * Dispatches an event to all the listeners. If a listener cancels an event, then no further
     * listeners will be informed of the event
     * @param event the event to dispatch
     * @return true if the event was cancelled. False otherwise
     * @throws NullPointerException if the event is null
     */
    public boolean dispatch( Event<L> event ) {
        if ( event == null )
            throw new NullPointerException( "Event cannot be null" );
        if ( listenersBaked == null )
            bake();
        for ( Listener listener : listenersBaked )
            if ( event.dispatch( (L)listener ) )
                return true;
        return false;
    }

    /**
     * Adds a listener to this event handler. A single listener can be registered multiple times at
     * multiple priorities, but only once at any one priority.
     * @param listener the listener to add
     * @throws NullPointerException if the listener is null
     */
    public void addListener( L listener, int priority ) {
        if ( listener == null )
            throw new NullPointerException( "Listener cannot be null" );

        // inform that a bake is needed
        listenersBaked = null;

        HashSet<Listener> list = listeners.get( priority );
        if ( list == null )
            listeners.put( priority, list = new HashSet<>() );
        list.add( listener );
    }

    /**
     * Removes a listener at the specified priority. If the priority of the listener is not known,
     * then {@link #removeAll(Listener)} can be used. However, this method is preferred as it has
     * significant speed improvements.
     * @param listener the listener to remove
     * @param priority the priority to remove the listener from
     * @return false if the listener was not registered at the given priority
     * @throws NullPointerException if the listener is null
     */
    public boolean removeListener( L listener, int priority ) {
        if ( listener == null )
            throw new NullPointerException( "Listener cannot be null" );
        HashSet<Listener> set = listeners.get( priority );
        boolean found = set != null && set.remove( listener );
        if ( found )
            listenersBaked = null;
        return found;
    }

    /**
     * Removes the listener fully. The listener will not be listening at any priority after this
     * call. Should it be known what level of priority it is listening at, then it is
     * recommended to call {@link #removeListener(Listener, int)} as it has much faster performance
     * than this method.
     * @param listener the listener to remove
     * @return false if the listener was not registered
     * @throws NullPointerException if the listener is null
     */
    public boolean removeAll( L listener ) {
        if ( listener == null )
            throw new IllegalArgumentException( "Listener cannot be null" );

        boolean found = false;
        for ( HashSet<Listener> set : listeners.values() )
            found = set.remove( listener ) || found;

        if ( found )
            listenersBaked = null;

        return found;
    }

    /**
     * Tests if there is any listeners registered. Before firing an event, it is recommended that
     * this method is checked; if no listeners are registered, then there is no point wasting
     * performance setting up events. Note: firing events with no listeners is very fast anyway,
     * thus, it's only when the set up to fire the event is strenuous does it become worth calling
     * this method.
     * @return true if there are no listeners.
     */
    public boolean isEmpty() {
        if ( listenersBaked == null )
            bake();
        return listenersBaked.length != 0;
    }

    /**
     * Bakes the cached listeners order
     */
    private void bake() {
        int size = 0;
        for( HashSet<Listener> list : listeners.values() )
            size += list.size();
        Listener[] listenersBaked = this.listenersBaked = new Listener[size];
        int i = 0;
        for( HashSet<Listener> set : listeners.descendingMap().values() ) {
            for ( Listener l : set ) {
                listenersBaked[i] = l;
                i++;
            }
        }
    }

    private void readObject( ObjectInputStream inputStream ) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        listenersBaked = null;
    }

}
