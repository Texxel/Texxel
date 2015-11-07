package com.github.texxel.sprites.imp;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.gameloop.GameBatcher;
import com.github.texxel.sprites.GameSprite;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.utils.GameTimer;
import com.github.texxel.utils.Point2D;

public abstract class AbstractVisual implements Visual {

    private GameSprite sprite;
    private float playTime = 0;
    private Animation animation;
    private TextureRegion lastFrame = null;
    private Point2D facing = Point2D.RIGHT;

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
            doFacingCorrection( facing, sprite );
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
     * Flips the sprite so that it is facing in the correct direction
     * @param facing the direction to turn the sprite
     * @param sprite the sprite to flip
     */
    protected void doFacingCorrection( Point2D facing, GameSprite sprite ) {
        if ( facing.x > 0 )
            sprite.setFlip( false, false );
        else if ( facing.x < 0 )
            sprite.setFlip( true, false );
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
    public Visual setLocation( float x, float y ) {
        getSprite().setPosition( x, y );
        return this;
    }

    @Override
    public Visual setDirection( Point2D dir ) {
        if ( dir == null )
            throw new NullPointerException( "'dir' cannot be null" );
        facing = dir;
        doFacingCorrection( facing, sprite );
        return this;
    }

    @Override
    public Point2D getDirection() {
        return facing;
    }

    @Override
    public float getRotation() {
        return sprite.getRotation();
    }

    @Override
    public Visual setRotation( float rotation ) {
        sprite.setRotation( rotation );
        return this;
    }
}
