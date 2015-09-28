package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;

public final class HeroIdleAI implements Brain {

    public HeroIdleAI( Char hero ) {
        hero.setGoal( new HeroIdleGoal( hero ) );
    }

    @Override
    public void update() {
        // nothing to do
    }
}
