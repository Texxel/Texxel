package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.actor.ActorSpawnEvent;

/**
 * The interface to use to listen to ActorSpawnEvents
 */
public interface ActorSpawnListener extends Listener {
    /**
     * Called whenever an actor is spawned
     */
    void onActorSpawned( ActorSpawnEvent e );
}
