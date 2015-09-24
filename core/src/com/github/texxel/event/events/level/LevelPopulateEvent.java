package com.github.texxel.event.events.level;

import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.Level;

public class LevelPopulateEvent extends LevelEvent<LevelConstructionListener> {

    public LevelPopulateEvent( Level level ) {
        super( level );
    }

    @Override
    public boolean dispatch( LevelConstructionListener listener ) {
        listener.onLevelPopulated( getLevel() );
        return false;
    }
}
