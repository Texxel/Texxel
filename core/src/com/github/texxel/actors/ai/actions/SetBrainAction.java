package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;

/**
 * The PassOfBrain action is designed so that when an actors current goal is finished, it can switch
 * out the brain to something else.
 */
public class SetBrainAction implements Action {

    private final Actor actor;
    private final Brain nextBrain;

    public SetBrainAction( Actor actor, Brain nextBrain ) {
        if ( actor == null )
            throw new NullPointerException( "'actor' cannot be null" );
        if ( nextBrain == null )
            throw new NullPointerException( "'nextBrain' cannot be null" );
        this.actor = actor;
        this.nextBrain = nextBrain;
    }

    @Override
    public void onStart() {
        actor.setBrain( nextBrain );
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public boolean render() {
        return true;
    }

    @Override
    public void forceFinish() {
        actor.setBrain( nextBrain );
    }

    @Override
    public void onFinish() {

    }

}

