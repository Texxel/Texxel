package com.github.texxel.sprites.api;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.gameloop.GameBatcher;
import com.github.texxel.sprites.GameSprite;

/**
 * A visual is a high level representation of something that can be drawn. It is often useful to
 * use a {@link GameSprite} to perform the lower level task but this is not necessary. Visuals should
 * not have any logic of their own; they should just get told what to do and obediently obey those
 * commands.
 */
public interface Visual {

    /**
     * Gets the Animation that is currently playing.
     * @return the current animation. Never returns null.
     */
    Animation getPlaying();

    /**
     * Sets the animation that is playing. If the animation is not different to the animation that
     * was previously playing, then nothing will happen.
     * @param animation the animation to play
     */
    void play( Animation animation );

    /**
     * Gets the x location of the sprite. 1 unit is equal to on world unit.
     * @return the sprites x location
     */
    float x();

    /**
     * Gets the y location of the sprite. 1 unit is equal to 1 world unit
     * @return the sprites y location
     */
    float y();

    /**
     * <p>Sets where the sprite is rendered. 1 unit is equal to one world unit. Sprites can be set
     * half way between cells. </p>
     * <p>Note: Setting a Visuals location only changes where the user sees the visual. It does not
     * change where the thing that the visual represents is drawn.</p>
     * @param x the x position
     * @param y the y position
     */
    void setLocation( float x, float y );

    /**
     * Renders the Visual to the GameBatcher
     * @param batcher the batcher that will (lazilly) draw something.
     */
    void render( GameBatcher batcher );

}
