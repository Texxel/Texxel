package com.github.texxel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.input.KeyboardEvent;
import com.github.texxel.event.events.input.ScreenTouchedEvent;
import com.github.texxel.event.listeners.input.KeyboardListener;
import com.github.texxel.event.listeners.input.ScreenTouchedListener;

/**
 * The touch listener takes over processing of inputs from the main application. Others can listen
 * into the event by registering themselves as an EventListener
 */
public final class InputHandler implements InputProcessor {


    static {
        Gdx.input.setInputProcessor( new InputHandler() );
    }

    private static final ScreenTouchedEvent.ScreenTouchedDownEvent touchDownEvent
            = new ScreenTouchedEvent.ScreenTouchedDownEvent( null );
    private static final ScreenTouchedEvent.ScreenTouchedUpEvent touchUpEvent
            = new ScreenTouchedEvent.ScreenTouchedUpEvent( null );
    private static final ScreenTouchedEvent.ScreenTouchedDraggedEvent touchDraggedEvent
            = new ScreenTouchedEvent.ScreenTouchedDraggedEvent( null, 0, 0 );
    private static final KeyboardEvent.KeyPressEvent keyPressEvent
            = new KeyboardEvent.KeyPressEvent( 0 );
    private static final KeyboardEvent.KeyReleaseEvent keyReleaseEvent
            = new KeyboardEvent.KeyReleaseEvent( 0 );
    private static final ScreenTouchedEvent.MouseScrolledEvent scrollEvent
            = new ScreenTouchedEvent.MouseScrolledEvent( 0 );
    private static final EventHandler<ScreenTouchedListener> touchEventHandler
            = new EventHandler<>();
    private static final EventHandler<KeyboardListener> keyEventHandler
            = new EventHandler<>();

    /**
     * Gets the EventHandle
     */
    public static EventHandler<ScreenTouchedListener> getTouchHandler() {
        return touchEventHandler;
    }

    public static EventHandler<KeyboardListener> getKeyHandler() {
        return keyEventHandler;
    }

    private static final int MAX_TOUCHES = 16;
    private ScreenTouchedEvent.Touch[] touches = new ScreenTouchedEvent.Touch[MAX_TOUCHES];

    private InputHandler() {
        // only one instance needed which is created in static block
    }

    private boolean dispatchKey( KeyboardEvent e ) {
        keyEventHandler.dispatch( e );
        return true;
    }

    @Override
    public boolean keyDown( int keycode ) {
        KeyboardEvent.KeyPressEvent e = keyPressEvent;
        e.setKeyCode( keycode );
        return dispatchKey( e );
    }

    @Override
    public boolean keyUp( int keycode ) {
        KeyboardEvent.KeyReleaseEvent e = keyReleaseEvent;
        e.setKeyCode( keycode );
        return dispatchKey( e );
    }

    @Override
    public boolean keyTyped( char character ) {
        return true;
    }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
        if ( pointer >= MAX_TOUCHES ) {
            Gdx.app.log( "InputHandler", "WARNING: max touches exceeded. Ignoring touch" );
            return true;
        }
        ScreenTouchedEvent.Touch touch = new ScreenTouchedEvent.Touch( screenX, screenY );
        touches[pointer] = touch;
        ScreenTouchedEvent e = touchDownEvent;
        e.setTouch( touch );
        touchEventHandler.dispatch( e );
        return true;
    }

    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button ) {
        if ( pointer >= MAX_TOUCHES )
            return true;
        ScreenTouchedEvent.Touch touch = touches[pointer];
        if ( touch == null ) {
            Gdx.app.log( "InputHandler", "WARNING: null touch reference on a touch up " );
            return true;
        }
        touch.setCurrent( screenX, screenY );
        // now forget about the old touch
        touches[pointer] = null;
        ScreenTouchedEvent e = touchUpEvent;
        e.setTouch( touch );
        touchEventHandler.dispatch( e );
        return true;
    }

    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer ) {
        if ( pointer >= MAX_TOUCHES )
            return true;
        ScreenTouchedEvent.Touch touch = touches[pointer];
        if ( touch == null ) {
            Gdx.app.log( "InputHandler", "WARNING: null touch reference on a touch dragged " );
            return true;
        }
        float deltaX = screenX - touch.xCurrent();
        float deltaY = screenY - touch.yCurrent();
        touch.setCurrent( screenX, screenY );
        ScreenTouchedEvent.ScreenTouchedDraggedEvent e = touchDraggedEvent;
        e.setTouch( touch );
        e.setDelta( deltaX, deltaY );
        touchEventHandler.dispatch( e );
        return true;
    }

    @Override
    public boolean mouseMoved( int screenX, int screenY ) {
        return true;
    }

    @Override
    public boolean scrolled( int amount ) {
        ScreenTouchedEvent.MouseScrolledEvent e = scrollEvent;
        e.setAmount( amount );
        touchEventHandler.dispatch( e );
        return true;
    }
}
