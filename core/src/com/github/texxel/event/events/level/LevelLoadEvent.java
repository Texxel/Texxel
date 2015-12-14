package com.github.texxel.event.events.level;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.level.LevelSaveListener;
import com.github.texxel.levels.Level;

public class LevelLoadEvent implements Event<LevelSaveListener> {

    private final Level level;

    public LevelLoadEvent( Level level ) {
        if ( level == null )
            throw new NullPointerException( "'level' cannot be null" );
        this.level = level;
    }

    @Override
    public boolean dispatch( LevelSaveListener listener ) {
        listener.onLevelLoaded( level );
        return false;
    }
}
