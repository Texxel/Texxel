package com.github.texxel.actors.ai;

import com.github.texxel.saving.Bundlable;

/**
 * A sensor is a long term item attached to a character. It listens for events in the world and sets
 * configures a correct brain.
 */
public interface Sensor extends Bundlable {

    /**
     * Called when the sensor is attached to a character
     */
    void onStart();

    /**
     * Called every turn of the character
     */
    void update();

    /**
     * Called when the senor is removed from the character
     */
    void onRemove();

}
