package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.utils.Point2D;

public class HeroMoveAI implements Brain {

    public static class Mover extends CharMoveGoal {
        private final Char hero;

        public Mover( Char hero, Point2D target ) {
            super( hero, target );
            this.hero = hero;
        }

        @Override
        public Action onTargetReached() {
            return new SetBrainAction( hero, new HeroIdleAI( hero ) );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( hero, new HeroIdleAI( hero ) );
        }
    }

    public HeroMoveAI( Char hero, Point2D target ) {
        hero.setGoal( new Mover( hero, target ) );
    }

    @Override
    public void update() {
    }
}
