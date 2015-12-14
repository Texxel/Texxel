package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.utils.Point2D;

public class HeroMoveAI implements Brain {

    static {
        ConstructorRegistry.put( HeroMoveAI.class, new Constructor<HeroMoveAI>() {
            @Override
            public HeroMoveAI newInstance( Bundle bundle ) {
                return new HeroMoveAI( bundle );
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

        public Mover( Char hero, Point2D target ) {
            super( hero, target );
        }

        public Mover( Bundle bundle ) {
            super( bundle );
        }

        @Override
        public Action onTargetReached() {
            return new SetBrainAction( getCharacter(), new HeroIdleAI( getCharacter() ) );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( getCharacter(), new HeroIdleAI( getCharacter() ) );
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            return super.bundle( topLevel );
        }

        @Override
        public void restore( Bundle bundle ) {
            super.restore( bundle );
        }
    }

    private Mover mover;

    public HeroMoveAI( Char hero, Point2D target ) {
        this.mover = new Mover( hero, target );
    }

    protected HeroMoveAI( Bundle bundle ) {
    }

    @Override
    public void update() {
        Char c = mover.getCharacter();
        c.setGoal( mover );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putNNBundlable( "mover", mover );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        mover = bundle.getNNBundlable( "mover" );
    }
}
