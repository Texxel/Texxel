package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Action;

/**
 * The DestroyAction will remove the actor from the level.
 */
public class DestroyAction implements Action {

    private final Actor actor;

    public DestroyAction( Actor actor ) {
        this.actor = actor;
    }

    @Override
    public void onStart() {
        actor.level().removeActor( actor );
    }

    @Override
    public boolean update( float dt ) {
        return true;
    }

    @Override
    public boolean render( float dt ) {
        return true;
    }

    @Override
    public void forceFinish() {

    }

    @Override
    public void onFinish() {

    }
}
