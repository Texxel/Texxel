package com.github.texxel.event.listeners.level;

import com.github.texxel.event.Listener;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.BundleGroup;

public interface LevelSaveListener extends Listener {

    /**
     * Called when a level is loaded from a save file. The passed bundle is read-only.
     * @param level the Level that was loaded
     * @param data the BundleGroup the level was restored from.
     */
    void onLevelLoaded( Level level, BundleGroup data );

    /**
     * Called whenever a level is saved.
     * @param level the level that gets saved
     * @param data the BundleGroup that the level is saved into
     */
    void onLevelSaved( Level level, BundleGroup data );

}
