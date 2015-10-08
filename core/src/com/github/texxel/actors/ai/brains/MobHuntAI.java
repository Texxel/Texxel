package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.utils.Point2D;

public class MobHuntAI implements Brain {

    private class Mover extends CharMoveGoal {

        public Mover( Char character, Point2D target ) {
            super( character, target );
        }

        @Override
        public Action onTargetReached() {
            // ATTACK!
            return new AttackAction( mob, hunted );
        }

        @Override
        public Action onCannotReachTarget() {
            // wait until we can reach them
            return new IdleAction( mob );
        }

    }

    final Char mob;
    final Mover mover;
    final Char hunted;

    /**
     * Creates a brain that's sole goal is to hunt the given opponent.
     * @param mob the character that is hunting
     * @param hunted the hunted character
     */
    public MobHuntAI( Char mob, Char hunted ) {
        if ( mob == null )
            throw new NullPointerException( "'character' cannot be null" );
        if ( hunted == null )
            throw new NullPointerException( "'hunted' cannot be null" );
        this.mob = mob;
        this.hunted = hunted;
        mover = new Mover( mob, hunted.getLocation() );
        mob.setGoal( mover );
    }

    @Override
    public void update() {
        if ( mob.getVision().isVisible( hunted.getLocation() ) && !hunted.isDead() ) {
            // follow them
            mover.setTarget( hunted.getLocation() );
        } else {
            // go wandering (but check where they were first)
            mob.setBrain( new MobWanderAI( mob, mover.getTarget() ) );
        }
    }
}
