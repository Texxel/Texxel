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

        Dungeon.register( 1, new RegularDescriptor( 1 ) );
        Dungeon.register( 2, new RegularDescriptor( 2 ) );
        Dungeon.register( 3, new RegularDescriptor( 3 ) );
        Dungeon.register( 4, new RegularDescriptor( 4 ) );
        Dungeon.register( 5, new RegularDescriptor( 5 ) );
    }

    private static class RegularDescriptor extends AbstractDescriptor {

        public RegularDescriptor( int id ) {
            super( id );
        }

        @Override
        protected Level constructLevel( int id, int width, int height ) {
            return new RegularLevel( id, width, height );
        }

    }

    public RegularLevel( int id, int width, int height ) {
        super( id, width, height );
    }

    protected RegularLevel( Bundle bundle ) {
        super( bundle );
    }

}