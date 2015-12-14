package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;

public final class HeroIdleAI implements Brain {

    private static final long serialVersionUID = 7653437046972326460L;
    private final Char hero;
    boolean updated = false;

    public HeroIdleAI( Char hero ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        this.hero = hero;
    }

    @Override
    public void update() {
        if ( !updated ) {
            hero.setGoal( new HeroIdleGoal( hero ) );
            updated = true;
        }
    }
}
