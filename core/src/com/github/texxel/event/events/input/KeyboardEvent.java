package com.github.texxel.event.events.input;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.input.KeyboardListener;

public abstract class KeyboardEvent implements Event<KeyboardListener> {

    public static class KeyPressEvent extends  KeyboardEvent {

        public KeyPressEvent( int keyCode ) {
            super( keyCode );
        }

        @Override
        public boolean dispatch( KeyboardListener listener ) {
            return listener.onKeyPressed( keyCode );
        }
    }

    public static class KeyReleaseEvent extends KeyboardEvent {

        public KeyReleaseEvent( int keyCode ) {
            super( keyCode );
        }

        @Override
        public boolean dispatch( KeyboardListener listener ) {
            return listener.onKeyReleased( keyCode );
        }
    }

    int keyCode;

    public KeyboardEvent( int keyCode ) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode( int keyCode ) {
        this.keyCode = keyCode;
    }

}
