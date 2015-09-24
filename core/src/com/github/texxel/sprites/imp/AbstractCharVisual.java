package com.github.texxel.sprites.imp;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.api.CharVisual;

public abstract class AbstractCharVisual extends AbstractVisual implements CharVisual {

    private Animation idleAnimation = null;
    private Animation operateAnimation = null;
    private Animation walkAnimation = null;
    private Animation dieAnimation = null;
    private Animation attackAnimation = null;


    /**
     * Gets the images used to draw this Hero. This method is only called once to construct
     * animations. If different textures are wanted later on, then the textures must be changed by
     * altering the animations.
     * @return the Hero's texture
     */
    protected abstract TextureRegion[][] frames();

    /**
     * A convenience method that makes an animation from the specified details. The method will lookup
     * the frames from the {@link #frames()} method. This method is only helpful if the animations
     * keyframes are all in the same row. If they are not, then this method is useless.
     * @param fps the speed of the animation
     * @param tier the row of frames to use. 0 is the first line
     * @param playMode the animations play mode.
     * @param frames the index of the frames to use. 0 is the left frame.
     * @return the created animation
     * @throws IndexOutOfBoundsException if tier or frames exceeds the bounds of the array returned
     * by {@link #frames()}
     * @throws NullPointerException if any selected frame is null
     * @throws NullPointerException if playMode is null
     */
    protected Animation makeAnimation( float fps, int tier, Animation.PlayMode playMode, int ... frames ) {
        TextureRegion[][] textureRegions = frames();
        TextureRegion[] selectedFrames = new TextureRegion[frames.length];
        for ( int i = 0; i < frames.length; i++) {
            selectedFrames[i] = textureRegions[tier][frames[i]];
            if ( selectedFrames[i] == null )
                throw new NullPointerException( "frame["+i+"] is null" );
        }
        if ( playMode == null )
            throw new NullPointerException( "playMode cannot be null" );
        Animation animation = new Animation( 1 / fps, selectedFrames );
        animation.setPlayMode( playMode );
        return animation;
    }

    @Override
    protected void playStartAction() {
        play( getIdleAnimation() );
    }

    @Override
    public Animation getIdleAnimation() {
        if ( idleAnimation == null )
            return idleAnimation = makeIdleAnimation();
        return idleAnimation;
    }

    @Override
    public Animation getAttackAnimation() {
        if ( attackAnimation == null )
            return attackAnimation = makeAttackAnimation();
        return attackAnimation;
    }

    @Override
    public Animation getDieAnimation() {
        if ( dieAnimation == null )
            return dieAnimation = makeDieAnimation();
        return dieAnimation;
    }

    @Override
    public Animation getOperateAnimation() {
        if ( operateAnimation == null )
            return operateAnimation = makeOperateAnimation();
        return operateAnimation;
    }

    @Override
    public Animation getWalkAnimation() {
        if ( walkAnimation == null )
            return walkAnimation = makeWalkAnimation();
        return walkAnimation;
    }

    protected abstract Animation makeAttackAnimation();

    protected abstract Animation makeDieAnimation();

    protected abstract Animation makeIdleAnimation();

    protected abstract Animation makeOperateAnimation();

    protected abstract Animation makeWalkAnimation();

}