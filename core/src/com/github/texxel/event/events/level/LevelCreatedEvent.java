package com.github.texxel.event.events.level;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.Level;

public class LevelCreatedEvent implements Event<LevelConstructionListener> {

    private final Level level;

    public LevelCreatedEvent( Level level ) {
        if ( level == null )
            throw new NullPointerException( "'level' cannot be null" );
        this.level = level;
    }

    @Override
    public boolean dispatch( LevelConstructionListener listener ) {
        listener.onLevelCreated( level );
        return false;
    }
}
