package com.github.texxel.actors.ai.brains;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.utils.Point2D;

public class MobWanderAI implements Brain {

    private class Mover extends CharMoveGoal {

        public Mover( Char character, Point2D target ) {
            super( character, target );
        }

        @Override
        public Action onTargetReached() {
            setTarget( Dungeon.level().randomRespawnCell() );
            return new IdleAction( mob );
        }

        @Override
        public Action onCannotReachTarget() {
            setTarget( Dungeon.level().randomRespawnCell() );
            return new IdleAction( mob );
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

    /**
     * Constructs a new wander AI that will begin to move the mob to a random spot in the level
     * @param mob the character to move. The AI is designed for mobs but will work for any Char.
     */
    public MobWanderAI( Char mob ) {
        this( mob, Dungeon.level().randomRespawnCell() );
    }

    @Override
    public void update() {
        // just keep moving
    }
}
