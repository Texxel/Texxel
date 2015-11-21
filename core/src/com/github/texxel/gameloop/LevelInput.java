package com.github.texxel.gameloop;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.input.CellSelectedEvent;
import com.github.texxel.event.events.input.ScreenTouchedEvent;
import com.github.texxel.event.listeners.input.ScreenTouchedListener;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.ui.InputHandler;

public class LevelInput implements GameInput, ScreenTouchedListener {

    /**
     * The handler used to inform that the user has pressed a button
     */
    public interface ClickHandler {
        /**
         * Called when the user taps on the screen. The passed coordinates are in world co-ordinates
         * @param x the x cell of the tap
         * @param y the y cell of the tap
         */
        void onClick( int x, int y );
    }

    /**
     * The minimum zoom
     * TODO make max/min zoom amounts less arbitrary
     */
    private static final float MIN_ZOOM = 0.3f;
    /**
     * The maximum zoom
     */
    private static final float MAX_ZOOM = 3.0f;
    /**
     * The amount of before the action is considered a drag (arbitrary scale)
     */
    private static final float DRAG_THRESHOLD = 100f;
    /**
     * The maximum clicks that can happen in a single tick
     */
    private static final int MAX_TOUCHES = 5;

    /** The camera to manipulate */
    private OrthographicCamera camera;
    /** When zooming, this is the starting distance between the fingers in screen distances */
    private float startSpan = -1;
    /** This is the zoom that the camera was at before pinching */
    private float startZoom;
    /** True when the user is zooming */
    private boolean pinching = false;
    /** True is the user is currently dragging */
    private boolean dragging = false;
    /** The first finger on the screen. Will never be null if touchB is null*/
    private ScreenTouchedEvent.Touch touchA = null;
    /** The 2nd finger on the screen. Will never contain something if touchA is null */
    private ScreenTouchedEvent.Touch touchB = null;
    /** The last place the player dragged in world coords*/
    private Vector3 lastPos = new Vector3();
    /** A temporary vector for random use in math calculations */
    private Vector3 temp = new Vector3();

    /** A buffered list of x clicks in the last tick */
    private final int[] xTouches = new int[MAX_TOUCHES];
    /** A buffered list of y clicks in the last tick */
    private final int[] yTouches = new int[MAX_TOUCHES];
    /** The index of the last touch read from the list */
    private int touchIndex = 0;
    /** The index of the last touch written to the list */
    private int touchTop = 0;

    public LevelInput( OrthographicCamera camera ) {
        System.out.println( "Creating " + this );
        this.camera = camera;
        InputHandler.getTouchHandler().addListener( this, EventHandler.NORMAL );
    }

    @Override
    public boolean onTouchUp( ScreenTouchedEvent.Touch touch ) {
        if ( touchA != touch && touchB != touch ) {
            // we don't care about other touches
            return false;
        }

        if ( pinching ) {
            // fall back to dragging
            initiateDrag( touchA == touch ? touchB : touchA );
        } else {
            if ( !dragging )
                throw new RuntimeException( "should always be dragging here" );
            if ( Vector2.dst2( touch.xCurrent(), touch.yCurrent(), touch.xStart(), touch.yStart() ) < DRAG_THRESHOLD )
                onClick( touch );
            // reset
            touchA = null;
            touchB = null;
            dragging = false;
            pinching = false;
        }
        return true;
    }

    @Override
    public boolean onTouchDown( ScreenTouchedEvent.Touch touch ) {
        System.out.println( "touch down: " + this );
        if ( touchA == null ) {
            // start dragging
            initiateDrag( touch );
        } else if ( touchB == null) {
            // one finger was down, now start pinching
            initiatePinch( touchA, touch );
        }
        // else ignore
        return true;
    }

    @Override
    public boolean onTouchDragged( ScreenTouchedEvent.Touch touch, float deltaX, float deltaY ) {
        ScreenTouchedEvent.Touch touchA = this.touchA;
        ScreenTouchedEvent.Touch touchB = this.touchB;

        if ( touchA == touch || touchB == touch ) {
            if ( pinching ) {
                processPinch();
            } else {
                if ( dragging ) {
                    processDrag();
                } else {
                    System.out.println( "moved fingers in too complex way!" );
                }
            }
        } else {
            // finger has joined after other fingers left
            if ( dragging ) {
                initiatePinch( touchA, touch );
            }
            // else ignore touch
        }
        // inform all plugins that the level has used up the event
        return true;
    }

    @Override
    public boolean onMouseScrolled( float amount ) {
        OrthographicCamera camera = this.camera;
        // this equation just feels nice - it's rather arbitrary
        camera.zoom += amount * camera.zoom * 0.1f;
        camera.zoom = MathUtils.clamp( camera.zoom, MIN_ZOOM, MAX_ZOOM );
        camera.update();
        return true;
    }

    private void initiateDrag( ScreenTouchedEvent.Touch touch ) {
        dragging = true;
        pinching = false;
        touchA = touch;
        touchB = null;
        lastPos.set( touch.xCurrent(), touch.yCurrent(), 0 );
        camera.unproject( lastPos );
    }

    private void processDrag() {
        ScreenTouchedEvent.Touch touch = this.touchA;
        final float xCurrent = touch.xCurrent();
        final float yCurrent = touch.yCurrent();
        final Vector3 temp = this.temp;
        final Vector3 lastPos = this.lastPos;
        final OrthographicCamera camera = this.camera;

        temp.set( xCurrent, yCurrent, 0 );
        camera.unproject( temp );

        temp.sub( lastPos );

        camera.position.sub( temp.x, temp.y, 0 );
        camera.update();

        lastPos.set( xCurrent, yCurrent, 0 );
        camera.unproject( lastPos );
        // note: cannot reuse un-projected values from temp because the world has moved
    }

    private void initiatePinch( ScreenTouchedEvent.Touch touchA, ScreenTouchedEvent.Touch touchB ) {
        pinching = true;
        dragging = false;
        this.touchA = touchA;
        this.touchB = touchB;
        startZoom = camera.zoom;
        startSpan = dist( touchA, touchB );
    }

    private void processPinch() {
        final ScreenTouchedEvent.Touch touchA = this.touchA;
        final ScreenTouchedEvent.Touch touchB = this.touchB;
        final float dist = dist( touchA, touchB );
        float zoom = startZoom * startSpan / ( dist + 0.001f );
        zoom = MathUtils.clamp( zoom, MIN_ZOOM, MAX_ZOOM );
        camera.zoom = zoom;
        camera.update();
    }

    private void onClick( ScreenTouchedEvent.Touch touch ) {
        Vector3 temp = this.temp;
        temp.set( touch.xCurrent(), touch.yCurrent(), 0 );
        camera.unproject( temp );
        int x = (int)temp.x;
        int y = (int)temp.y;
        xTouches[touchTop] = x;
        yTouches[touchTop] = y;
        touchTop = (touchTop+1) % MAX_TOUCHES;
    }

    @Override
    public void process( Level level ) {
        int touchIndex = this.touchIndex;
        int touchTop   = this.touchTop;
        int[] xTouches = this.xTouches;
        int[] yTouches = this.yTouches;
        for ( ; touchIndex != touchTop; touchIndex = (touchIndex+1)%MAX_TOUCHES ) {
            level.getCellSelectHandler().dispatch(
                    new CellSelectedEvent( level, xTouches[touchIndex], yTouches[touchIndex] ) );
        }
        this.touchIndex = touchIndex;
    }

    private static float dist( ScreenTouchedEvent.Touch a, ScreenTouchedEvent.Touch b ) {
        float xDist = a.xCurrent() - b.xCurrent();
        float yDist = a.yCurrent() - b.yCurrent();
        return (float)Math.sqrt( xDist*xDist + yDist*yDist );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        throw new UnsupportedOperationException( "Level input does not support being saved" );
    }

    @Override
    public void restore( Bundle bundle ) {
        throw new UnsupportedOperationException( "Level input does not support being saved" );
    }

    @Override
    public void onDestroy() {
        System.out.println( "Destroying " + this );
        InputHandler.getTouchHandler().removeAll( this );
    }
}
