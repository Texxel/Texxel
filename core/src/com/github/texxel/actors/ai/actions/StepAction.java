package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.gameloop.CameraMover;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.GameTimer;
import com.github.texxel.utils.Point2D;

public class StepAction implements Action {

    private static final float STEP_TIME = 0.1f;

    private final Char character;
    private CharVisual charVisual;
    private float xDelta, yDelta;
    private float xStart, yStart;
    private Point2D target;
    private float timeElapsed = 0;
    private boolean finishImmediately;

    public StepAction( Char character, Point2D target ) {
        if ( character == null )
            throw new NullPointerException( "'character' cannot be null" );
        if ( target == null )
            throw new NullPointerException( "'target' cannot be null" );
        this.character = character;
        this.target = target;
    }

    @Override
    public void onStart() {
        Point2D start = character.getLocation();
        // do the world update bit
        Point2D end = character.setLocation( target );

        if ( start.equals( end ) ) {
            // no need to do anything
            finishImmediately = true;
            return;
        }

        // TODO remove hardcoded walk time
        character.spend( 1.0f );

        charVisual = character.getVisual();
        // set up the graphics
        xStart = charVisual.x();
        yStart = charVisual.y();
        xDelta = end.x - charVisual.x();
        yDelta = end.y - charVisual.y();
        Animation walkAnimation = charVisual.getWalkAnimation();
        charVisual.play( walkAnimation );
        charVisual.setDirection( Point2D.direction( xDelta, yDelta ) );
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public boolean render() {
        if ( finishImmediately )
            return true;

        timeElapsed += GameTimer.tickTime();
        float factorComplete = timeElapsed / STEP_TIME;
        float x = xStart + xDelta * factorComplete;
        float y = yStart + yDelta * factorComplete;
        charVisual.setLocation( x, y );

        // TODO camera following hero is very hacky
        if ( character instanceof Hero ) {
            CameraMover.instance.camera.position.set( x, y, 0 );
            CameraMover.instance.camera.update();
        }

        // finished if we're over on the next step
        return (timeElapsed + GameTimer.tickTime()) / STEP_TIME >= 1;
    }

    @Override
    public void onFinish() {
        charVisual.setLocation( target.x, target.y );

        // note: the sprite will not be perfectly aligned to the grid when finished and we do not
        // want to align it because it causes the movement to look very choppy.
    }
}
