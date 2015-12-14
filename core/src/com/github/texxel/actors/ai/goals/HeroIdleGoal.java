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

    private static final long serialVersionUID = -4139081687576164557L;
    private final Char hero;

    public HeroIdleGoal( Char hero ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        this.hero = hero;
    }

    @Override
    public void onStart() {
    }

    @Override
    public Action nextAction() {
        return new IdleAction( hero, 0 );
    }

    @Override
    public void onRemove() {

    }
}
