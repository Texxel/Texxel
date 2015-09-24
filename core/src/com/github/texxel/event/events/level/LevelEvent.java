package com.github.texxel.event.events.level;

import com.github.texxel.event.Event;
import com.github.texxel.event.Listener;
import com.github.texxel.levels.Level;

public abstract class LevelEvent<T extends Listener> implements Event<T> {

    private final Level level;

    public LevelEvent( Level level ) {
        if ( level == null )
            throw new NullPointerException( "'level' cannot be null" );
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

}
