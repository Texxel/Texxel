package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.goals.CharDieGoal;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

public class CharDieAI implements Brain {

    static {
        ConstructorRegistry.put( CharDieAI.class, new Constructor<CharDieAI>() {
            @Override
            public CharDieAI newInstance( Bundle bundle ) {
                return new CharDieAI( bundle );
            }
        } );
    }

    public CharDieAI( Char ch ) {
        ch.setGoal( new CharDieGoal( ch ) );
    }

    protected CharDieAI( Bundle bundle ) {
    }

    @Override
    public void update() {

    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        return topLevel.newBundle();
    }

    @Override
    public void restore( Bundle bundle ) {
    }
}
