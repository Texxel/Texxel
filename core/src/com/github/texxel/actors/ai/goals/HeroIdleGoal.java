package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.IdleAction;

/**
 * The HeroIdleGoal just lets the hero sit around and wait. It was designed for the hero but will
 * work with any character
 */
public final class HeroIdleGoal implements Goal {

    private final Action idleAction;

    public HeroIdleGoal( Char hero ) {
        idleAction = new IdleAction( hero, 0 );
    }
    
    @Override
    public void onStart() {
    }

    @Override
    public Action nextAction() {
        return idleAction;
    }

    @Override
    public void onRemove() {

    }
}
