package com.github.texxel.actors.ai.state;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.State;
import com.github.texxel.actors.ai.actions.IdleAction;

public class HeroIdleState implements State {

    private final Char hero;

    public HeroIdleState( Char hero ) {
        this.hero = hero;
    }

    @Override
    public void onStart() {
        hero.setNextAction( new IdleAction( hero ) );
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void update() {

    }
}
