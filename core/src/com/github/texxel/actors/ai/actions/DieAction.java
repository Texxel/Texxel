package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.GameTimer;

public class DieAction implements Action {

    private final Char character;
    private float timeToFinish;

    public DieAction( Char character ) {
        this.character = character;
    }

    @Override
    public void onStart() {
        CharVisual charVisual = character.getVisual();
        Animation animation = charVisual.getDieAnimation();
        charVisual.play( animation );
        timeToFinish = animation.getAnimationDuration();
    }

    @Override
    public boolean update() {
        timeToFinish -= GameTimer.tickTime();
        if ( timeToFinish <= 0 ) {
            character.level().removeActor( character );
            return true;
        } else
            return false;
    }

    @Override
    public boolean render() {
        // TODO make character fade out
        return true;
    }

    @Override
    public void onFinish() {

    }
}
