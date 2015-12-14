package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.utils.Point2D;

public class MobHuntAI implements Brain {

    static {
        ConstructorRegistry.put( MobHuntAI.class, new Constructor<MobHuntAI>() {
            @Override
            public MobHuntAI newInstance( Bundle bundle ) {
                return new MobHuntAI( bundle );
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

        private MobHuntAI ai;

        public Mover( MobHuntAI ai, Char character, Point2D target ) {
            super( character, target );
            this.ai = ai;
        }

        Mover( Bundle bundle ) {
            super( bundle );
        }

        @Override
        public Action onTargetReached() {
            // ATTACK!
            return new AttackAction( ai.mob, ai.hunted );
        }

        @Override
        public Action onCannotReachTarget() {
            // wait until we can reach them
            return new IdleAction( ai.mob );
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            Bundle bundle = super.bundle( topLevel );
            Bundle data = topLevel.newBundle();
            data.putBundlable( "ai", ai );
            bundle.putBundle( Mover.class.toString(), data );
            return bundle;
        }

        @Override
        public void restore( Bundle bundle ) {
            super.restore( bundle );
            Bundle data = bundle.getBundle( Mover.class.toString() );
            ai = data.getBundlable( "ai" );
        }
    }

    Char mob;
    Mover mover;
    Char hunted;

    /**
     * Creates a brain that's sole goal is to hunt the given opponent.
     * @param mob the character that is hunting
     * @param hunted the hunted character
     */
    public MobHuntAI( Char mob, Char hunted ) {
        if ( mob == null )
            throw new NullPointerException( "'character' cannot be null" );
        if ( hunted == null )
            throw new NullPointerException( "'hunted' cannot be null" );
        this.mob = mob;
        this.hunted = hunted;
        this.mover = new Mover( this, mob, hunted.getLocation() );
        mob.setGoal( mover );
    }

    protected MobHuntAI( Bundle bundle ) {
    }

    @Override
    public void update() {
        if ( mob.getVision().isVisible( hunted.getLocation() ) && !hunted.isDead() ) {
            // follow them
            mover.setTarget( hunted.getLocation() );
        } else {
            // go wandering (but check where theys were first)
            mob.setBrain( new MobWanderAI( mob, mover.getTarget() ) );
        }
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putBundlable( "mob", mob );
        bundle.putBundlable( "hunted", hunted );
        bundle.putBundlable( "mover", mover );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        mob = bundle.getBundlable( "mob" );
        hunted = bundle.getBundlable( "hunted" );
        mover = bundle.getBundlable( "mover" );
    }
}
