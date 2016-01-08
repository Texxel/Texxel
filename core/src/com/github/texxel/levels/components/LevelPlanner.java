package com.github.texxel.levels.components;

import java.io.Serializable;
import java.util.Collection;

/**
 * A LevelPlanner decides where rooms are laid out in a level. A LevelPlaner does not need to
 * decide the room types - however it can choose some of them if it wants/needs to.
 */
public interface LevelPlanner extends Serializable {

    /**
     * Plots out the layout of a level with the specified dimensions.
     * @param width the width of the level
     * @param height the height of the level
     * @return the planed layout of the level
     */
    Collection<Room> planRooms( int width, int height );
}
