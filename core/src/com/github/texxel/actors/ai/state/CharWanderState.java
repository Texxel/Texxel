package com.github.texxel.actors.ai.state;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.utils.Point2D;

public class CharWanderState extends CharMoveState {

    public CharWanderState( Char mob ) {
        super( mob );
    }

    @Override
    public void update() {
        // look for enemies
        Char character = this.getCharacter();
        for ( Char c : Dungeon.level().getCharacters() ) {
            if ( c instanceof Hero ) {
                Point2D loc = c.getLocation();
                if ( character.getVision().isVisible( loc.x, loc.y ) ) {
                    character.setState( new CharHuntState( character ) );
                    return;
                }
            }
        }
        // no enemies found. Continue to wander
        super.update();
    }

    @Override
    public void onTargetReached() {
        setTarget( Dungeon.level().randomRespawnCell() );
    }

    @Override
    public void onCannotReachTarget() {
        setTarget( Dungeon.level().randomRespawnCell() );
    }

    @Override
    public void onNoTarget() {
        setTarget( Dungeon.level().randomRespawnCell() );
    }
}
