package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.tiles.Tile;
import com.github.texxel.tiles.Trampleable;
import com.github.texxel.utils.GameTimer;
import com.github.texxel.utils.Point2D;

public class StepAction implements Action {

    private static final float STEP_TIME = 0.12f;

    private final Char character;
    private CharVisual charVisual;
    private float xDelta, yDelta;
    private float xStart, yStart;
    private final Point2D target;
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

        // trample the tiles
        Tile tile = character.level().getTileMap().getTile( end.x, end.y );
        Tile trampleTo;
        if ( tile instanceof Trampleable ) {
            trampleTo = ( (Trampleable) tile ).onTrample( character );
            if ( trampleTo == null )
                throw new NullPointerException( "Tile '" + tile + "' said to trample to null!" );
            if ( trampleTo != tile ) {
                character.level().getTileMap().setTile( end.x, end.y, trampleTo );
            }
        }

        // un trample the tiles
        tile = character.level().getTileMap().getTile( start.x, start.y );
        if ( tile instanceof Trampleable ) {
            trampleTo = ( (Trampleable) tile ).onLeave( character );
            if ( trampleTo == null )
                throw new NullPointerException( "Tile '" + tile + "' said to un-trample to null!" );
            if ( trampleTo != tile ) {
                character.level().getTileMap().setTile( start.x, start.y, trampleTo );
            }
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

        // finished if we're over on the next step
        return (timeElapsed + GameTimer.tickTime()) / STEP_TIME >= 1;
    }

    @Override
    public void onFinish() {
        charVisual.setLocation( target.x, target.y );

        // note: the sprite will not be perfectly aligned to the grid when finished and we do not
        // want to align it because it causes the movement to look very choppy.
    }

    @Override
    public void forceFinish() {

    }
}
