package com.github.texxel.event.listeners.input;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.input.ScreenTouchedEvent;

/**
 * Listens to every interaction on the screen by the user
 */
public interface ScreenTouchedListener extends Listener {
    /**
     * Called whenever a finger is lifted of the screen
     * @return true to consume the event
     */
    boolean onTouchUp( ScreenTouchedEvent.Touch touch );

    /**
     * Called whenever a finger first touches the screen
     * @return true to consume the event
     */
    boolean onTouchDown( ScreenTouchedEvent.Touch touch );

    /**
     * Called whenever a finger is dragged
     * @return true to consume the event
     */
    boolean onTouchDragged( ScreenTouchedEvent.Touch touch, float deltaX, float deltaY );

    /**
     * Called when the mouse wheel is scrolled.
     * @return true if consume the event
     */
    boolean onMouseScrolled( float amount );
}
