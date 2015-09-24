package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.Char;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.Point2D;

public class IdleAction implements Action {

    private final Char character;

    public IdleAction( Char character ) {
        this.character = character;
    }

    @Override
    public void onStart() {
        CharVisual visual = character.getVisual();
        Point2D location = character.getLocation();
        visual.setLocation( location.x, location.y );
        visual.play( visual.getIdleAnimation() );
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public boolean finished() {
        return true;
    }

    @Override
    public boolean finishedGraphics() {
        return true;
    }
}
