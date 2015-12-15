package com.github.texxel.sprites.api;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * An animation visual is something that knows how to update itself
 */
public interface AnimationVisual extends Visual {

    /**
     * Gets the Animation that is currently playing.
     * @return the current animation. Never returns null.
     */
    Animation getPlaying();

    /**
     * Sets the animation that is playing. If the animation is not different to the animation that
     * was previously playing, then nothing will happen.
     * @param animation the animation to play
     * @return  this
     */
    Visual play( Animation animation );

}
