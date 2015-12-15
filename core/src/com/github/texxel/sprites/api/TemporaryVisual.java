package com.github.texxel.sprites.api;

public interface TemporaryVisual extends Visual {

    /**
     * Destroys the visual. Any further calls to any method (other than {@link #isDestroyed()}) may
     * produce undefined results
     */
    void destroy();

    /**
     * Tests if the visual is destroyed
     * @return true if the visual is destroyed
     */
    boolean isDestroyed();

}
