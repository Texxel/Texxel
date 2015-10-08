package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.utils.Point2D;

/**
 * The HeroHuntAI is what the Hero uses when chasing down an enemy
 */
public class HeroHuntAI implements Brain {

    private class Mover extends CharMoveGoal {
        public Mover( Char character, Point2D target ) {
            super( character, target );
        }

        @Override
        public Action onTargetReached() {
            return new AttackAction( hero, enemy );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( hero, new HeroIdleAI( hero ) );
        }
    }

    protected final Char hero, enemy;
    private final Mover mover;

    public HeroHuntAI( Char hero, Char enemy ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        if ( enemy == null )
            throw new NullPointerException( "'enemy' cannot be null" );
        this.hero = hero;
        this.enemy = enemy;
        mover = new Mover( hero, enemy.getLocation() );
        hero.setGoal( mover );
    }

    @Override
    public void update() {
        // run at enemy until can not see them
        if ( hero.getVision().isVisible( enemy.getLocation() ) && !enemy.isDead() )
            mover.setTarget( enemy.getLocation() );
        else
            hero.setBrain( new HeroIdleAI( hero ) );
    }
}