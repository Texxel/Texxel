package com.github.texxel.levels.components;

import com.github.texxel.levels.Level;

/**
 * LevelDescriptors describes the relationship between levels and how the hero should transverse
 * levels. If an object needs to have a persistent reference to a level, but does not require the
 * level data, it is much better to use a LevelDescriptor as the reference because it uses much less
 * memory and time to create and lets the garbage collector clean up the level data.
 */
public interface LevelDescriptor {

    /**
     * Constructs the level. Note: implementations of this interface should <i>not</i> cache the
     * created level as it would mean that the garbage collector couldn't clean up. Thus, every call
     * to this method must create/load a new Level (which is an expensive task!).
     * @return the created level
     */
    Level constructLevel();

    int width();

    int height();

    void setSize( int width, int height );

    LevelBuilder getBuilder();

    void setBuilder( LevelBuilder builder );

    LevelDecorator getDecorator();

    void setDecorator( LevelDecorator decorator );

    int id();

}
