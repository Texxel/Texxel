package com.github.texxel.levels;

import com.github.texxel.Dungeon;
import com.github.texxel.levels.components.AbstractDescriptor;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

public class RegularLevel extends AbstractLevel {

    static {
        ConstructorRegistry.put( RegularLevel.class, new Constructor<RegularLevel>() {
            @Override
            public RegularLevel newInstance( Bundle bundle ) {
                return new RegularLevel( bundle );
            }
        } );
    }

    public static class RegularDescriptor extends AbstractDescriptor {

        public RegularDescriptor( Dungeon dungeon, int id ) {
            super( dungeon, id );
        }

        @Override
        protected Level constructLevel( Dungeon dungeon, int id, int width, int height ) {
            return new RegularLevel( dungeon, id, width, height );
        }

    }

    public RegularLevel( Dungeon dungeon, int id, int width, int height ) {
        super( dungeon, id, width, height );
    }

    protected RegularLevel( Bundle bundle ) {
        super( bundle );
    }

}