package com.github.texxel.event.listeners.level;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.level.LevelPlanEvent;
import com.github.texxel.levels.Level;

public interface LevelConstructionListener extends Listener {

    /**
     * Called as the very first step to designing a level. Anything can be changed at this point.
     * Most changes should just edit methods in the LevelDescriptor gotten from
     * {@link LevelPlanEvent#getLevel()}. However, an entirely differernt level can be chosen with
     * by setting a different LevelDescriptor.
     * @param e say what you want here
     */
    void onLevelPlan( LevelPlanEvent e );

    /**
     * Called directly after the LevelBuilder and LevelDecorator has done their work. At this point,
     * the basic tile layout has finished but there are no mobs or items. It is best to change tiles
     * in this event as there is no chance of effecting things like mobs
     * @param level the level
     * LevelDecorator paid any attention to it)
     */
    void onTilesPlaced( Level level );

    /**
     * Called after the level has asked to be populated.
     * @param level the level
     */
    void onLevelPopulated( Level level );

    /**
     * Called as a final step to the Level creation. Small edits can be made here, but try to keep
     * this event as a read-only event.
     * @param level the level
     */
    void onLevelCreated( Level level );

}
