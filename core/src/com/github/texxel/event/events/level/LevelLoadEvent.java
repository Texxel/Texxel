package com.github.texxel.event.events.level;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.level.LevelSaveListener;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.BundleGroup;

public class LevelLoadEvent implements Event<LevelSaveListener> {

    private final Level level;
    private final BundleGroup data;

    public LevelLoadEvent( Level level, BundleGroup data ) {
        if ( level == null )
            throw new NullPointerException( "'level' cannot be null" );
        if ( data == null )
            throw new NullPointerException( "'data' cannot be null" );
        this.level = level;
        this.data = data;
    }

    @Override
    public boolean dispatch( LevelSaveListener listener ) {
        listener.onLevelLoaded( level, data );
        return false;
    }
}
