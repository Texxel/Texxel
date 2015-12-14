package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

/**
 * The HeroIdleGoal just lets the hero sit around and wait. It was designed for the hero but will
 * work with any character
 */
public final class HeroIdleGoal implements Goal {

    static {
        ConstructorRegistry.put( HeroIdleGoal.class, new Constructor<HeroIdleGoal>() {
            @Override
            public HeroIdleGoal newInstance( Bundle bundle ) {
                return new HeroIdleGoal( bundle );
            }
        } );
    }

    private Char hero;

    public HeroIdleGoal( Char hero ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        this.hero = hero;
    }

    protected HeroIdleGoal( Bundle bundle ) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public Action nextAction() {
        return new IdleAction( hero, 0 );
    }

    @Override
    public void onRemove() {

    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putBundlable( "hero", hero );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        hero = bundle.getBundlable( "hero" );
    }
}
