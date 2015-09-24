package com.github.texxel.event.listeners.level;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.level.LevelDestructionEvent;

public interface LevelDestructionListener extends Listener {

    void onLevelDestructed( LevelDestructionEvent e );
}
