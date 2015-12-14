package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.utils.Point2D;

public class MobWanderAI implements Brain {

    private static final long serialVersionUID = 8389856891668374287L;

    private static class Mover extends CharMoveGoal {

        private static final long serialVersionUID = 1975072061783890167L;

        public Mover( Char character, Point2D target ) {
            super( character, target );
        }

        @Override
        public Action onTargetReached() {
            setTarget( getCharacter().level().randomRespawnCell() );
            return new IdleAction( getCharacter() );
        }

        @Override
        public Action onCannotReachTarget() {
            setTarget( getCharacter().level().randomRespawnCell() );
            return new IdleAction( getCharacter() );
        }
    }

    final Char mob;
    final Mover mover;

    /**
     * Constructs a wandering ai
     * @param mob the mob to control
     * @param target the first place the mob should wander to
     */
    public MobWanderAI( Char mob, Point2D target ) {
        this.mob = mob;
        mover = new Mover( mob, target );
        mob.setGoal( mover );
    }

    @Override
    public void update() {
        // just keep moving
    }
}
