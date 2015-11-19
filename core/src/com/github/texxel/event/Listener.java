package com.github.texxel.event;

import com.github.texxel.saving.Bundlable;

/**
 * All event listeners should extend this interface. Listeners must implement the bundlable interface
 * so that the {@link EventHandler} class be saved
 */
public interface Listener extends Bundlable {
}
