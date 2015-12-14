package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

public final class HeroIdleAI implements Brain {

    static {
        ConstructorRegistry.put( HeroIdleAI.class, new Constructor<HeroIdleAI>() {
            @Override
            public HeroIdleAI newInstance( Bundle bundle ) {
                return new HeroIdleAI( bundle );
            }
        } );
    }

    boolean updated = false;
    private Char hero;

    public HeroIdleAI( Char hero ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        this.hero = hero;
    }

    protected HeroIdleAI( Bundle bundle ) {

    }

    @Override
    public void update() {
        if ( !updated ) {
            hero.setGoal( new HeroIdleGoal( hero ) );
            updated = true;
        }
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
