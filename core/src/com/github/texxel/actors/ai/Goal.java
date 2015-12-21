package com.github.texxel.actors.ai;

import java.io.Serializable;

/**
 * Goals are what an Actor is currently trying to do. A goal should be rather basic (e.g. walk from
 * here to there). A goal's only job is to provide a stream of actions for an Actor to do so that the
 * goal can be completed.
 */
public interface Goal extends Serializable {

    /**
     * Called when an Actor has the state attached to it. This should be used to perform any start
     * up actions
     */
    void onStart();

    /**
     * Decides on the next action to perform. This does not set the actors current action but simply
     * states what it should be.
     * @return the actor's next action. Should never be null
     */
    Action nextAction();

    /**
     * Called when this state is removed from the actor
     */
    void onRemove();

}
