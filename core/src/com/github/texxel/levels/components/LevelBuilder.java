package com.github.texxel.levels.components;

import java.util.Collection;

public interface LevelBuilder {

    /**
     * Plots out the layout of a level with the specified dimensions
     * @param width the width of the level
     * @param height the height of the level
     * @return the planed layout of the level
     */
    Collection<Room> planRooms( int width, int height );
}
