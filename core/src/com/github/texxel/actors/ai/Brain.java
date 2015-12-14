package com.github.texxel.actors.ai;

import java.io.Serializable;

/**
 * A Brain is an Actors AI. A Brain is updated every turn of the actors and it is the Brains
 * responsibility to make sure that actor has a correctly set {@link Goal}. A Brain shouldn't directly
 * change anything except for the attached actors goal.
 */
public interface Brain extends Serializable {

    void update();

}
