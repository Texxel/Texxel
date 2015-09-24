package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.actor.CharMoveEvent;

public interface CharMoveListener extends Listener {
    void onCharMove( CharMoveEvent e );
}
