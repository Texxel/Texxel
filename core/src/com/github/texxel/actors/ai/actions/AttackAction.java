package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.Char;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.GameTimer;

public class AttackAction implements Action {

    private final Char attacker, defender;
    private boolean finished = false;
    private float timeToFinish;

    public AttackAction( Char attacker, Char defender ) {
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void onStart() {
        CharVisual visual = attacker.getVisual();
        Animation animation = visual.getAttackAnimation();
        timeToFinish = animation.getAnimationDuration();
        visual.play( visual.getAttackAnimation() );
    }

    @Override
    public void update() {
        if ( timeToFinish <= 0 ) {
            attacker.attack( defender );
            finished = true;
        }
    }

    @Override
    public void render() {
        timeToFinish -= GameTimer.tickTime();
        if ( timeToFinish <= 0 ) {
            // reset animation
            CharVisual visual = attacker.getVisual();
            visual.play( visual.getIdleAnimation() );
        }
    }

    @Override
    public boolean finished() {
        return finished;
    }

    @Override
    public boolean finishedGraphics() {
        return finished;
    }
}
