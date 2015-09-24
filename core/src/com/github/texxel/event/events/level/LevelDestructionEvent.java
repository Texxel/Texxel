package com.github.texxel.event.events.level;

import com.github.texxel.event.listeners.level.LevelDestructionListener;
import com.github.texxel.levels.Level;

public class LevelDestructionEvent extends LevelEvent<LevelDestructionListener> {

    public LevelDestructionEvent( Level level ) {
        super( level );
    }

    @Override
    public boolean dispatch( LevelDestructionListener listener ) {
        listener.onLevelDestructed( this );
        return false;
    }

}
