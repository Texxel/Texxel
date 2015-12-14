package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.utils.Point2D;

public class MobWanderAI implements Brain {

    static {
        ConstructorRegistry.put( MobWanderAI.class, new Constructor<MobWanderAI>() {
            @Override
            public MobWanderAI newInstance( Bundle bundle ) {
                return new MobWanderAI( bundle );
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

        public Mover( Char character, Point2D target ) {
            super( character, target );
        }

        private Mover( Bundle bundle ) {
            super( bundle );
        }

        @Override
        public Action onTargetReached() {
            setTarget( getCharacter().level().randomRespawnCell() );
            return new IdleAction( getCharacter() );
        }

        @Override
        public Action onCannotReachTarget() {
            setTarget( getCharacter().level().randomRespawnCell() );
            return new IdleAction( getCharacter() );
        }
    }

    Char mob;
    Mover mover;

    /**
     * Constructs a wandering ai
     * @param mob the mob to control
     * @param target the first place the mob should wander to
     */
    public MobWanderAI( Char mob, Point2D target ) {
        this.mob = mob;
        mover = new Mover( mob, target );
        mob.setGoal( mover );
    }

    protected MobWanderAI( Bundle bundle ) {

    }

    /**
     * Constructs a new wander AI that will begin to move the mob to a random spot in the level
     * @param mob the character to move. The AI is designed for mobs but will work for any Char.
     */
    public MobWanderAI( Char mob ) {
        this( mob, mob.level().randomRespawnCell() );
    }

    @Override
    public void update() {
        // just keep moving
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putBundlable( "mob", mob );
        bundle.putBundlable( "mover", mover );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        mob = bundle.getBundlable( "mob" );
        mover = bundle.getBundlable( "mover" );
    }
}
