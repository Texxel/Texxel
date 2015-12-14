package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.Point2D;

/**
 * The idle action skips a characters turn and plays its idle action.
 */
public class IdleAction implements Action {

    private final Char character;
    private float idleTime;

    public IdleAction( Char character, float idleTime ) {
        if ( character == null )
            throw new NullPointerException( "'character' cannot be null" );
        this.character = character;
        this.idleTime = idleTime;
    }

    public IdleAction( Char character ) {
        this( character, 1 );
    }

    @Override
    public void onStart() {
        CharVisual visual = character.getVisual();
        Point2D loc = character.getLocation();
        visual.setLocation( loc.x, loc.y );
        visual.play( visual.getIdleAnimation() );
    }

    @Override
    public boolean update() {
        character.spend( idleTime );
        return true;
    }

    @Override
    public boolean render() {
        return true;
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void forceFinish() {
        character.spend( idleTime );
    }
}
