package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;

/**
 * The ChangeGoalAction is designed so that when an actors current goal is finished, it can
 * switch out the current goal to something else. This can be done by simply setting the Goal
 * manually, but sometimes an action is required to be returned, so this gives a neat way to return
 * an action and change the goal. If the same ChangeGoalAction is asked to update more than once it
 * is assumed that a programming error has occurred that is creating an infinite loop and will crash
 * the program.
 */
public class ChangeGoalAction implements Action {

    private final Actor actor;
    private final Goal nextGoal;
    private boolean updated = false;

    public ChangeGoalAction( Actor actor, Goal nextGoal ) {
        if ( actor == null )
            throw new NullPointerException( "'actor' cannot be null" );
        if ( nextGoal == null )
            throw new NullPointerException( "'nextGoal' cannot be null" );
        this.actor = actor;
        this.nextGoal = nextGoal;
    }

    @Override
    public void onStart() {
        actor.setGoal( nextGoal );
    }

    @Override
    public boolean update( float dt ) {
        if ( updated )
            throw new IllegalStateException( "The same ChangeGoalAction updated twice in actor " + actor
            + " and setting next goal of " + nextGoal );
        else
            updated = true;
        return true;
    }

    @Override
    public boolean render( float dt ) {
        return true;
    }

    @Override
    public void forceFinish() {
        actor.setGoal( nextGoal );
    }

    @Override
    public void onFinish() {

    }

}

