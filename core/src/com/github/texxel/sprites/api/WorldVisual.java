package com.github.texxel.sprites.api;


/**
 * A WorldVisual is something that can be seen in the world but is not directly responsible for
 * drawing itself. A WorldVisual passes all its drawing tasks to onto a {@link Visual}.
 */
public interface WorldVisual {

    /**
     * The {@link Visual} that will be used to draw this object
     * @return a visual
     */
    Visual getVisual();

}
