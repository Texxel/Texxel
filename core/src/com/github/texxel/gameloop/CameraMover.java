package com.github.texxel.gameloop;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.texxel.Dungeon;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.input.CellSelectedEvent;
import com.github.texxel.event.events.input.ScreenTouchedEvent;
import com.github.texxel.event.listeners.input.ScreenTouchedListener;
import com.github.texxel.levels.Level;
import com.github.texxel.ui.InputHandler;

public class CameraMover implements ScreenTouchedListener {

    public static CameraMover instance;

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

    float startSpan = -1;
    float startZoom;
    boolean pinching = false;
    boolean dragging = false;
    ScreenTouchedEvent.Touch touchA = null;
    ScreenTouchedEvent.Touch touchB = null;
    public final OrthographicCamera camera;
    Vector3 lastPos = new Vector3();
    Vector3 temp = new Vector3();

    public CameraMover( OrthographicCamera camera ) {
        this.camera = camera;
        instance = this;
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
        // inform the level that it has been clicked
        // TODO level clicking could have a better place?
        Vector3 temp = this.temp;
        temp.set( touch.xCurrent(), touch.yCurrent(), 0 );
        camera.unproject( temp );
        int x = (int)temp.x;
        int y = (int)temp.y;
        Level level = Dungeon.level();
        level.getCellSelectHandler().dispatch(
                new CellSelectedEvent( x, y ) );
    }

    private static float dist( ScreenTouchedEvent.Touch a, ScreenTouchedEvent.Touch b ) {
        float xDist = a.xCurrent() - b.xCurrent();
        float yDist = a.yCurrent() - b.yCurrent();
        return (float)Math.sqrt( xDist*xDist + yDist*yDist );
    }
}
