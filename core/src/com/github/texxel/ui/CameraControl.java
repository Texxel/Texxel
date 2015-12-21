package com.github.texxel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * The Camera control is what lets the user drag/zoom the camera around the screen
 */
public class CameraControl extends GestureDetector.GestureAdapter implements EventListener {

    private final OrthographicCamera gameCamera;
    private final Camera uiCamera;
    private final GestureDetector detector;
    private final Vector3 coords = new Vector3(), coords2 = new Vector3();

    private float initialZoom = 1;
    private float preInitialDistance = 1;

    public CameraControl( OrthographicCamera gameCamera, Camera uiCamera ) {
        if ( gameCamera == null )
            throw new NullPointerException( "'gameCamera' cannot be null" );
        if ( uiCamera == null )
            throw new NullPointerException( "'uiCamera' cannot be null" );
        this.gameCamera = gameCamera;
        this.uiCamera = uiCamera;
        detector = new GestureDetector( 0, 0.4f, 1.1f, 0.15f, this );

        initialZoom = gameCamera.zoom;
    }

    @Override
    public boolean handle( Event event ) {
        InputEvent e;
        if ( event instanceof InputEvent )
            e = (InputEvent)event;
        else
            return false;

        InputEvent.Type type = e.getType();

        // All touch events are converted into screen coords for gesture detector.
        // Coords cannot be converted directly into world coords because the world coords move
        if ( type == InputEvent.Type.touchDown || type == InputEvent.Type.touchUp
                || type == InputEvent.Type.touchDragged ) {
            coords.set( e.getStageX(), e.getStageY(), 0 );
            uiCamera.project( coords );
            // flip the y axis direction (was assumed up but we want down for the screen)
            coords.y = Gdx.graphics.getHeight() - coords.y - 1;
        }


        switch ( type ) {
            case touchDown:
                detector.touchDown( coords.x, coords.y, e.getPointer(), e.getButton() );
                return true;
            case touchUp:
                detector.touchUp( coords.x, coords.y, e.getPointer(), e.getButton() );
                return true;
            case touchDragged:
                detector.touchDragged( coords.x, coords.y, e.getPointer() );
                return true;
            case scrolled:
                mouseScrolled( e.getScrollAmount() );
                return true;
            default:
                return false;
        }
    }

    private boolean mouseScrolled( int amount ) {
        OrthographicCamera camera = gameCamera;
        // this equation just feels nice - it's rather arbitrary
        camera.zoom += amount * camera.zoom * 0.1f;
        camera.zoom = MathUtils.clamp( camera.zoom, 0.3f, 4.0f );
        camera.update();
        return true;
    }

    @Override
    public boolean pan( float x, float y, float deltaX, float deltaY ) {
        Camera camera = gameCamera;

        // convert screen coords into world coords
        Vector3 coords = this.coords.set( deltaX, deltaY, 0 );
        Vector3 coords2 = this.coords2.set( 0, 0, 0 );
        camera.unproject( coords );
        camera.unproject( coords2 );
        deltaX = coords.x - coords2.x;
        deltaY = coords.y - coords2.y;

        // move the camera
        camera.position.sub( deltaX, deltaY, 0 );
        camera.update();
        return true;
    }

    @Override
    public boolean zoom( float initialDistance, float distance ) {
        OrthographicCamera gameCamera = this.gameCamera;

        if ( preInitialDistance != initialDistance ) {
            preInitialDistance = initialDistance;
            initialZoom = gameCamera.zoom;
        }

        float ratio = initialDistance / distance;
        gameCamera.zoom = initialZoom * ratio;
        gameCamera.zoom = MathUtils.clamp( gameCamera.zoom, 0.3f, 4.0f );
        gameCamera.update();
        return false;
    }
}
