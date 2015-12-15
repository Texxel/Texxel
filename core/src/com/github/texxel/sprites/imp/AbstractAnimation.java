package com.github.texxel.sprites.imp;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.api.AnimationVisual;
import com.github.texxel.sprites.api.Visual;

public abstract class AbstractAnimation extends AbstractVisual implements AnimationVisual {

    private float playTime = 0;
    private Animation animation;

    @Override
    public void update( float dt) {
        playTime += dt;
    }

    @Override
    public Animation getPlaying() {
        if ( animation == null ) {
            animation = getStartAnimation();
            if ( animation == null )
                throw new NullPointerException( "Starting animation returned null in class " + getClass() );
        }
        return animation;
    }

    @Override
    public Visual play( Animation animation ) {
        if ( animation == null )
            throw new NullPointerException( "'animation' cannot be null" );
        // don't update unless setting a new animation
        if ( this.animation != animation ) {
            this.animation = animation;
            this.playTime = 0;
        }
        return this;
    }

    /**
     * Gets the first animation to play
     * @return the first animation. Never null
     */
    protected abstract Animation getStartAnimation();

    @Override
    public TextureRegion getRegion() {
        return getPlaying().getKeyFrame( playTime );
    }
}
