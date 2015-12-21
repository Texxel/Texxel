package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;

/**
 * The HeroHuntAI is what the Hero uses when chasing down an enemy
 */
public class HeroHuntAI implements Brain {

    private static final long serialVersionUID = 5123151792941768153L;

    private class Mover extends CharMoveGoal {

        private static final long serialVersionUID = -3060928595245595803L;

        public Mover( Char hero, Char enemy ) {
            super( hero, enemy.getLocation() );
        }

        @Override
        public Action onTargetReached() {
            // do one hit then stop fighting
            hero.setBrain( new HeroIdleAI( hero ) );
            return new AttackAction( hero, enemy );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( hero, new HeroIdleAI( hero ) );
        }

    }

    private final Char hero, enemy;
    private final Mover mover;

    public HeroHuntAI( Char hero, Char enemy ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        if ( enemy == null )
            throw new NullPointerException( "'enemy' cannot be null" );
        this.hero = hero;
        this.enemy = enemy;
        mover = new Mover( hero, enemy );
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

    public Char hero() {
        return hero;
    }

    public Char enemy() {
        return enemy;
    }
}