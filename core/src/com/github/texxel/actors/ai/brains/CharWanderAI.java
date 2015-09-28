package com.github.texxel.actors.ai.brains;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.utils.Point2D;

public class CharWanderAI implements Brain {

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

    public CharWanderAI( Char mob, Point2D target ) {
        this.mob = mob;
        mover = new Mover( mob, target );
        mob.setGoal( mover );
    }

    @Override
    public void update() {
        // look for enemies
        // TODO move enemy search to sensors
        for ( Char c : Dungeon.level().getCharacters() ) {
            if ( c instanceof Hero ) {
                Point2D loc = c.getLocation();
                if ( mob.getVision().isVisible( loc ) ) {
                    mob.setBrain( new CharHuntAI( mob, c ) );
                    return;
                }
            }
        }
    }
}
