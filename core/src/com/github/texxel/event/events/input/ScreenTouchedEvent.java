package com.github.texxel.event.events.input;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.input.ScreenTouchedListener;

/**
 * The ScreenTouchedEvent is fired every time that a user touches the screen.
 */
public abstract class ScreenTouchedEvent implements Event<ScreenTouchedListener> {

    public static class ScreenTouchedUpEvent extends ScreenTouchedEvent {
        public ScreenTouchedUpEvent( Touch touch ) {
            super( touch );
        }
        @Override
        public boolean dispatch( ScreenTouchedListener listener ) {
            return listener.onTouchUp( touch );
        }
    }

    public static class ScreenTouchedDownEvent extends ScreenTouchedEvent {
        public ScreenTouchedDownEvent( Touch touch ) {
            super( touch );
        }
        @Override
        public boolean dispatch( ScreenTouchedListener listener ) {
            return listener.onTouchDown( touch );
        }
    }

    public static class ScreenTouchedDraggedEvent extends ScreenTouchedEvent {

        private float deltaX, deltaY;

        public ScreenTouchedDraggedEvent( Touch touch, float deltaX, float deltaY ) {
            super( touch );
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
        @Override
        public boolean dispatch( ScreenTouchedListener listener ) {
            return listener.onTouchDragged( touch, deltaX, deltaY );
        }

        public float getDeltaX() {
            return deltaX;
        }

        public float getDeltaY() {
            return deltaY;
        }

        public void setDelta( float x, float y ) {
            this.deltaX = x;
            this.deltaY = y;
        }
    }

    public static class MouseScrolledEvent implements Event<ScreenTouchedListener> {

        private float amount;

        public MouseScrolledEvent( float amount ) {
            this.amount = amount;
        }

        /**
         * The direction scrolled, will be -1 or +1
         * @return the amount scrolled by
         */
        public float getAmount() {
            return amount;
        }

        public void setAmount( float amount ) {
            this.amount = amount;
        }

        @Override
        public boolean dispatch( ScreenTouchedListener listener ) {
            return listener.onMouseScrolled( amount );
        }
    }

    /**
     * A Touch represents a finger on the screen. A single Touch instance is created every time that
     * another finger touches a screen and the Touch instance is persistent for that finger for the
     * entire duration of the touch.
     */
    public static class Touch {
        final float xStart, yStart;
        float xCurrent, yCurrent;

        public Touch( float xStart, float yStart ) {
            this.xStart = this.xCurrent = xStart;
            this.yStart = this.yCurrent = yStart;
        }

        public float xStart() {
            return xStart;
        }

        public float yStart() {
            return yStart;
        }

        public float xCurrent() {
            return xCurrent;
        }

        public float yCurrent() {
            return yCurrent;
        }

        public void setCurrent( float x, float y ) {
            this.xCurrent = x;
            this.yCurrent = y;
        }

        @Override
        public String toString() {
            return "Touch: (" + xCurrent + ", " + yCurrent + ")";
        }
    }

    Touch touch;

    public ScreenTouchedEvent( Touch touch ) {
        this.touch = touch;
    }

    public void setTouch( Touch touch ) {
        this.touch = touch;
    }

}
