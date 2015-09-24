package com.github.texxel.event.events.level;

import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.Level;

public class LevelTilePlacedEvent extends LevelEvent<LevelConstructionListener> {

    public LevelTilePlacedEvent( Level level ) {
        super( level );
    }

    @Override
    public boolean dispatch( LevelConstructionListener listener ) {
        listener.onTilesPlaced( getLevel() );
        return false;
    }
}
