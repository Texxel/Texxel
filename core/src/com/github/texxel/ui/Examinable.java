package com.github.texxel.ui;

import com.badlogic.gdx.graphics.g2d.Animation;

public interface Examinable {

    /**
     * Gets the name of the objected.
     */
    String name();

    /**
     * Gets short description of the object
     */
    String description();

    /**
     * Gets an animation to display. Implementations should note that the returned animation may be
     * mutated so a clone should be made.
     */
    Animation getLogo();


}
