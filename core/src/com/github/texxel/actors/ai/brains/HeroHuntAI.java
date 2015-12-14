package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

/**
 * The HeroHuntAI is what the Hero uses when chasing down an enemy
 */
public class HeroHuntAI implements Brain {

    static {
        ConstructorRegistry.put( HeroHuntAI.class, new Constructor<HeroHuntAI>() {
            @Override
            public HeroHuntAI newInstance( Bundle bundle ) {
                return new HeroHuntAI( bundle );
            }
        } );
    }

    private static class Mover extends CharMoveGoal {

        static {
            ConstructorRegistry.put( Mover.class, new Constructor<Mover>() {
                @Override
                public Mover newInstance( Bundle bundle ) {
                    return new Mover( bundle );
                }
            } );
        }

        private Char hero, enemy;

        public Mover( Char hero, Char enemy ) {
            super( hero, enemy.getLocation() );
            this.hero = hero;
            this.enemy = enemy;
        }

        private Mover( Bundle bundle ) {
            super( bundle );
        }

        @Override
        public Action onTargetReached() {
            return new AttackAction( hero, enemy );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( hero, new HeroIdleAI( hero ) );
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            Bundle parent = super.bundle( topLevel );
            Bundle bundle = topLevel.newBundle();
            bundle.putBundlable( "hero", hero );
            bundle.putBundlable( "enemy", enemy );
            parent.putBundle( Mover.class.toString(), bundle );
            return parent;
        }

        @Override
        public void restore( Bundle bundle ) {
            super.restore( bundle );
            Bundle data = bundle.getBundle( Mover.class.toString() );
            hero = data.getBundlable( "hero" );
            enemy = data.getBundlable( "enemy" );
        }
    }

    private Char hero, enemy;
    private Mover mover;

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

    protected HeroHuntAI( Bundle bundle ) {

    }

    @Override
    public void update() {
        // run at enemy until can not see them
        if ( hero.getVision().isVisible( enemy.getLocation() ) && !enemy.isDead() )
            mover.setTarget( enemy.getLocation() );
        else
            hero.setBrain( new HeroIdleAI( hero ) );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putBundlable( "hero", hero );
        bundle.putBundlable( "enemy", enemy );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        hero = bundle.getBundlable( "hero" );
        enemy = bundle.getBundlable( "enemy" );
        mover = bundle.getBundlable( "mover" );
    }

    public Char hero() {
        return hero;
    }

    public Char enemy() {
        return enemy;
    }
}