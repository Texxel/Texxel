package com.github.texxel.actors.mobs;

import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.api.MobVisual;
import com.github.texxel.sprites.imp.mobvisuals.RatVisual;
import com.github.texxel.utils.Point2D;

public class Rat extends AbstractMob {
    private static final Constructor<Rat> CONSTRUCTOR = new Constructor<Rat>() {
        @Override
        public Rat newInstance( Bundle bundle ) {
            return new Rat( bundle );
        }

    };
    static {
        ConstructorRegistry.put( Rat.class, CONSTRUCTOR );
    }

    private final RatVisual visual = new RatVisual();

    protected Rat( Bundle bundle ) {
        super( bundle );
    }

    public Rat( Level level, Point2D spawn ) {
        super( level, spawn, 6 );
    }

    @Override
    public MobVisual getVisual() {
        return visual;
    }

    @Override
    public String name() {
        return "rat";
    }

    @Override
    public String description() {
        return "EEK! A icky rat!";
    }
}
