package com.github.texxel.levels;

import com.github.texxel.Dungeon;
import com.github.texxel.levels.components.AbstractDescriptor;
import com.github.texxel.levels.components.LevelDescriptor;

public class RegularLevel extends AbstractLevel {

    private static final long serialVersionUID = -7459864167674779762L;

    public static class RegularDescriptor extends AbstractDescriptor {

        private static final long serialVersionUID = -6931904725512777814L;

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

    /**
     * Returned the level descriptor registered in the dungeon with and id of {@code id+1}
     * @return the previous level
     */
    @Override
    public LevelDescriptor getLevelBelow() {
        return dungeon().getDescriptor( id() + 1 );
    }

    /**
     * Returned the level descriptor registered in the dungeon with and id of {@code id-1}
     * @return the previous level
     */
    @Override
    public LevelDescriptor getLevelAbove() {
        return dungeon().getDescriptor( id() - 1 );
    }

}