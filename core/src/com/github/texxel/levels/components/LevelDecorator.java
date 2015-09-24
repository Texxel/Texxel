package com.github.texxel.levels.components;

import com.github.texxel.levels.Level;

import java.util.Collection;

public interface LevelDecorator {

    /**
     * Decorates a level
     * @param level the level to decorate
     * @param rooms the rooms layout. The collection cannot be modified
     */
    void decorate( Level level, Collection<Room> rooms );

}
