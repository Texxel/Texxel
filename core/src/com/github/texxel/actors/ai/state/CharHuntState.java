package com.github.texxel.actors.ai.state;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.State;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.utils.Point2D;

public class CharHuntState implements State {

    private class Mover extends CharMoveState {

        public Mover( Char character ) {
            super( character );
        }

        @Override
        public void onTargetReached() {
            if ( hunted != null )
                character.setNextAction( new AttackAction( character, hunted ) );
        }

        @Override
        public void onCannotReachTarget() {
            // wait until he can be reached
            character.spend( 1.0f );
        }

        @Override
        public void onNoTarget() {
            if ( hunted == null )
                character.setState( new CharWanderState( character ) );
            else
                setTarget( hunted.getLocation() );
        }
    }

    final Char character;
    final Mover mover;
    Char hunted;

    public CharHuntState( Char character ) {
        this.character = character;
        mover = new Mover( character );
    }

    @Override
    public void onStart() {
        mover.onStart();
    }

    @Override
    public void update() {
        int minDistance = Integer.MAX_VALUE;
        Char hunt = null;
        Point2D loc = character.getLocation();
        for ( Char c : Dungeon.level().getCharacters() ) {
            // TODO enemy shouldn't be detected by being an instance of Hero
            Point2D otherLoc = c.getLocation();
            if ( c instanceof Hero && character.getVision().isVisible( c.getLocation() ) ) {
                int d = PathFinder.gridDistance( loc.x, loc.y, otherLoc.x, otherLoc.y );
                if ( d < minDistance ) {
                    hunt = c;
                    minDistance = d;
                }
            }
        }
        if ( hunt == null ) {
            // continue walking to enemy was last seen
            mover.update();
        } else {
            hunted = hunt;
            mover.setTarget( hunt.getLocation() );
            mover.update();
        }
    }

    @Override
    public void onRemove() {
        mover.onRemove();
    }
}
