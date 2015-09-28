package com.github.texxel.actors.ai;

/**
 * An action is something that an actor can do. Actions should be very specific and contain no
 * intelligent AI (e.g move one cell up). At any moment, multiple actions can be rendering, but only
 * one action can be updating. Furthermore, only one action can be rendering for a single actor.
 */
public interface Action {

    /**
     * Called when the action is started for an actor
     */
    void onStart();

    /**
     * Updates the state of the action. Once the action indicates that it has finished, no further
     * calls to update will be made but there may be more calls to render.
     * @return true when the action is finished
     */
    boolean update();

    /**
     * Changes the graphics to match the state of the action. Never change the world state in this
     * method as it may stuff up other actions. When this action's rending is complete, this method
     * will return true. An action can never have the rendering finished before the updating thus
     * the output will be ignored until the updating is finished.
     * @return true when the rendering is finished
     */
    boolean render();

    /**
     * Called when the action is completely finished
     */
    void onFinish();

}
