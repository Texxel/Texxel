package com.github.texxel.sprites.imp;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.gameloop.GameBatcher;
import com.github.texxel.sprites.GameSprite;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.utils.GameTimer;

public abstract class AbstractVisual implements Visual {

    private GameSprite sprite;
    private float playTime = 0;
    private Animation animation;
    private TextureRegion lastFrame = null;

    @Override
    public void render( GameBatcher batcher ) {
        Animation animation = getPlaying();
        TextureRegion frame = animation.getKeyFrame( playTime );

        GameSprite sprite = getSprite();
        batcher.draw( sprite );

        // don't tell the sprite to update texture unless something has changed
        if ( frame != lastFrame ) {
            lastFrame = frame;
            sprite.setRegion( lastFrame );
        }

        playTime += GameTimer.tickTime();
    }

    /**
     * Gets the sprite that is used to draw things with. Changes made to the returned Visual
     * may permanently effect how the GameSprite is seen.
     * @return the Visual used to draw this object
     */
    protected GameSprite getSprite() {
        GameSprite sprite = this.sprite;
        if ( sprite == null ) {
            Animation animation = getPlaying();
            TextureRegion region = animation.getKeyFrame( 0 );
            this.sprite = sprite = new GameSprite( region, 0 );
            sprite.setSize( region.getRegionWidth() / 16f, region.getRegionHeight() / 16f );
        }

        return sprite;
    }

    @Override
    public Animation getPlaying() {
        if ( animation == null )
            playStartAction();
        return animation;
    }

    @Override
    public void play( Animation animation ) {
        if ( animation == null )
            throw new NullPointerException( "'animation' cannot be null" );
        // don't update unless setting a new animation
        if ( this.animation != animation ) {
            this.animation = animation;
            this.playTime = 0;
        }
    }

    /**
     * Starts playing the actions first action.
     */
    protected abstract void playStartAction();

    @Override
    public float x() {
        return getSprite().getX();
    }

    @Override
    public float y() {
        return getSprite().getY();
    }

    @Override
    public void setLocation( float x, float y ) {
        getSprite().setPosition( x, y );
    }
}
