package com.github.texxel.actors.ai;

import com.github.texxel.saving.Bundlable;

/**
 * Goals are what an Actor is currently trying to do. A goal should be rather basic (e.g. walk from
 * here to there). A goal's only job is to provide a stream of actions for an Actor to do so that the
 * goal can be completed.
 */
public interface Goal extends Bundlable {

    /**
     * Called when an Actor has the state attached to it
     */
    void onStart();

    /**
     * Decides on the next action to perform. This does not set the actors current action but simply
     * states what it should be.
     * @return the next action. Should never be null
     */
    Action nextAction();

    /**
     * Called this state is removed from the action
     */
    void onRemove();

}
