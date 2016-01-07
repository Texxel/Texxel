package com.github.texxel.event.listeners.level;

import com.github.texxel.event.Listener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.levels.components.Room;

import java.util.Collection;

/**
 * The LevelConstructionListener can listen to and alter how a level is made. A level construction
 * goes through multiple phases:
 * <ol>
 *     <li>Listeners are notified: {@link #onConstructionStarted(LevelDescriptor)}</li>
 *     <li>The Level is built</li>
 *     <li>Listeners are notified: {@link #onLevelInitialised(LevelDescriptor, Level)}</li>
 *     <li>The Planner is told to plan the level</li>
 *     <li>Listeners are informed: {@link #onLevelPlanned(LevelDescriptor, Level, Collection)}</li>
 *     <li>The decorators are told to decorate the level</li>
 *     <li>The listeners are informed: {@link #onLevelCreated(LevelDescriptor, Level, Collection)}</li>
 * </ol>
 */
public interface LevelConstructionListener extends Listener {

    /**
     * Called before the level is constructed. This gives listeners a chance to configure the
     * LevelDescriptor as they like.
     * @param constructor the constructor that will build the level
     */
    void onConstructionStarted( LevelDescriptor constructor );

    /**
     * Called after the level has been created but before anything else has been done.
     * @param descriptor the descriptor the level is created from
     * @param level the created level
     */
    void onLevelInitialised( LevelDescriptor descriptor, Level level );

    /**
     * Called when the level has been planned out.
     * @param level the level that is getting planned
     * @param rooms the rooms used to make this level. The list cannot be modified.
     */
    void onLevelPlanned( LevelDescriptor descriptor, Level level, Collection<Room> rooms );

    /**
     * Called when the level has been constructed. No further actions will occur after this.
     * @param level the constructed level
     * @param rooms the rooms used to make this level. The list cannot be modified.
     */
    void onLevelCreated( LevelDescriptor descriptor, Level level, Collection<Room> rooms );
}
