package com.github.texxel.event.listeners.level;

import com.github.texxel.event.Listener;
import com.github.texxel.levels.Level;

public interface LevelSaveListener extends Listener {

    /**
     * Called when a level is loaded from a save file.
     * @param level the Level that was loaded
     */
    void onLevelLoaded( Level level );

    /**
     * Called whenever a level is saved.
     * @param level the level that gets saved
     */
    void onLevelSaved( Level level );

}
