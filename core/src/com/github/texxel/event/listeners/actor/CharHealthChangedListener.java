package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.actor.CharHealthChangedEvent;

public interface CharHealthChangedListener extends Listener {

    void onHealthChanged( CharHealthChangedEvent e );

}
