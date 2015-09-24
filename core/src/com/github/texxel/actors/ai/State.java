package com.github.texxel.actors.ai;

/**
 * A state is a high level controller of an actor; it can be thought of as the actors AI. A state
 * is updated each turn the actor gets and provides the actor with some {@link Action}s.
 */
public interface State {

    /**
     * Called when the state is added to a character
     */
    void onStart();

    /**
     * Called when the character should update the characters current action.
     */
    void update();

    /**
     * Called when the character removes this state
     */
    void onRemove();

}
