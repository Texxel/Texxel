package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.DieAction;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

public class CharDieGoal implements Goal {

    static {
        ConstructorRegistry.put( CharDieGoal.class, new Constructor<CharDieGoal>() {
            @Override
            public CharDieGoal newInstance( Bundle bundle ) {
                return new CharDieGoal( bundle );
            }
        } );
    }

    private Char ch;

    public CharDieGoal( Char ch ) {
        this.ch = ch;
    }

    protected CharDieGoal( Bundle bundle ) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public Action nextAction() {
        return new DieAction( ch );
    }

    @Override
    public void onRemove() {

    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putBundlable( "char", ch );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        ch = bundle.getBundlable( "char" );
    }
}
