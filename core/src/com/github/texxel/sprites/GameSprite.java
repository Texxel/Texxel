package com.github.texxel.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A Visual is an extension of a {@link Sprite} but with an added depth. A Visual can only
 * represent a single still image. All visible things in vanilla Texxel use a Visual to represent
 * themselves at some point. However, the Visual is rarely used on its own; a {@link com.github.texxel.sprites.api.Visual} is
 * generally used as a higher level wrapper around a GameSprite to control various states and animations.
 */
public class GameSprite extends Sprite {

    private int depth = 0;

    public GameSprite( TextureRegion region, int depth ) {
        super( region );
        this.depth = depth;
    }

    @Override
    public void set( Sprite sprite ) {
        super.set( sprite );
        if ( sprite instanceof GameSprite )
            setDepth( ( (GameSprite) sprite ).getDepth() );
        else
            depth = 0;
    }

    /**
     * Sets the depth that this sprite is at. The depth can be thought of as the distance that the
     * sprite is <i>into</i> the screen. Thus, sprites with a small (or large negative) depth will
     * be rendered first
     * //TODO is visual depth backwards?
     * @param depth the sprites depth
     */
    public void setDepth( int depth ) {
        this.depth = depth;
    }

    /**
     * Gets the sprites distance into the screen
     * @return the sprites depth
     * @see #setDepth(int)
     */
    public int getDepth() {
        return depth;
    }

}
