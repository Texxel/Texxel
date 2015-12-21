package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.actor.CharDamagedEvent;

public interface CharDamagedListener extends Listener {

    void onCharDamaged( CharDamagedEvent e );

}
