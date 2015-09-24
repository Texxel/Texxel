package com.github.texxel.event.listeners.input;

import com.github.texxel.event.Listener;

public interface KeyboardListener extends Listener {
    /**
     * Called when a key is pressed.
     * @param keyCode the key that was pressed
     * @return true to consume the event (so lower level things don't respond to the event)
     */
    boolean onKeyPressed( int keyCode );
    /**
     * Called when a key is released.
     * @param keyCode the key that was released
     * @return true to consume the event (so lower level things don't respond to the event)
     */
    boolean onKeyReleased( int keyCode );
}
