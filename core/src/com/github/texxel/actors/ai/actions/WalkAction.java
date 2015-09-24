package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.gameloop.CameraMover;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.GameTimer;
import com.github.texxel.utils.Point2D;

public class WalkAction implements Action {

    private final Char character;
    private CharVisual charVisual;
    private Point2D start, end;
    private int xDelta, yDelta;
    private float timeElapsed = 0;
    private float totalTime;
    private boolean finished = false;

    public WalkAction( Char character, Point2D target ) {
        this.character = character;
        this.end = target;
    }

    @Override
    public void onStart() {
        start = character.getLocation();

        // do the world update bit
        end = character.setLocation( end );
        // if the move was cancelled, finish
        if ( end == null ) {
            finished = true;
            return;
        }
        // TODO remove hardcoded attack time
        character.spend( 1.0f );

        // set up the graphics
        xDelta = end.x - start.x;
        yDelta = end.y - start.y;
        charVisual = character.getVisual();
        Animation walkAnimation = charVisual.getWalkAnimation();
        totalTime = 0.1f;
        charVisual.play( walkAnimation );
        charVisual.setLocation( start.x, start.y );
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        timeElapsed += GameTimer.tickTime();
        float factorComplete = timeElapsed / totalTime;
        float x = start.x + xDelta * factorComplete;
        float y = start.y + yDelta * factorComplete;
        charVisual.setLocation( x, y );
        if ( character instanceof Hero ) {
            CameraMover.instance.camera.position.set( x, y, 0 );
            CameraMover.instance.camera.update();
        }
        if ( factorComplete >= 1 ) {
            finished = true;
            // make the alignment perfect
            //charVisual.setLocation( end.x, end.y );
        }
    }

    @Override
    public boolean finished() {
        return true;
    }

    @Override
    public boolean finishedGraphics() {
        return finished;
    }
}
