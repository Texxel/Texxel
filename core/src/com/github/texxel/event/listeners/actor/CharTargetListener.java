package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.actor.CharTargetEvent;

public interface CharTargetListener extends Listener {
    void onCharTarget( CharTargetEvent e );
}
