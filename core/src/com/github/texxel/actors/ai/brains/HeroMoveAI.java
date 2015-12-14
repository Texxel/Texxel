package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.utils.Point2D;

public class HeroMoveAI implements Brain {

    private static final long serialVersionUID = 4148643608173747749L;

    private static class Mover extends CharMoveGoal {

        private static final long serialVersionUID = 6915437453698082620L;

        public Mover( Char hero, Point2D target ) {
            super( hero, target );
        }

        @Override
        public Action onTargetReached() {
            return new SetBrainAction( getCharacter(), new HeroIdleAI( getCharacter() ) );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( getCharacter(), new HeroIdleAI( getCharacter() ) );
        }

    }

    private final Mover mover;

    public HeroMoveAI( Char hero, Point2D target ) {
        this.mover = new Mover( hero, target );
    }

    @Override
    public void update() {
        Char c = mover.getCharacter();
        c.setGoal( mover );
    }

}
