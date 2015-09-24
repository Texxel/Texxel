package com.github.texxel.actors.ai;

public interface Action {

    void onStart();

    void update();

    void render();

    /**
     * This should return true when the action has finished updating the world. Once this returns
     * true, <b>never</b> make a change to the world logic
     * @return true if the action has finished updating the world
     */
    boolean finished();

    /**
     * Tests if the action has finished updating the graphics.
     * @return true if the graphics updating has finished
     */
    boolean finishedGraphics();

}
