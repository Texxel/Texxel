package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.actor.ActorDestroyEvent;

public interface ActorDestroyListener extends Listener {
    void onActorRemoved( ActorDestroyEvent e );
}
