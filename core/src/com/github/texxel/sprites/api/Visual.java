package com.github.texxel.sprites.api;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.utils.Point2D;

/**
 * A visual is a high level representation of something that can be drawn. Visuals try to have
 * minimals logic of their own; they should just get told what to do and obediently obey those
 * commands.
 */
public interface Visual {

    /**
     * Gets the region the visual is displaying
     * @return the region this visual displays
     */
    TextureRegion getRegion();

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
     * Gets this visuals depth. Big negative numbers are rendered on top. See {@link #setDepth(int)}
     * for more detail.
     * @return the visuals depth to render at
     */
    int depth();

    /**
     * Sets the depth for this visual. The depth is in the opposite direction to the z axis (which
     * follows the standard right hand convention of being positive out of the screen). Thus, visuals
     * with a very high depth will be drawn at the back and visuals with a very small (i.e. large
     * negative depth will be drawn on the top. Most things should get drawn at the default depth
     * of 0. Tiles are drawn at depth 100.
     * @param depth the visuals new depth
     * @return this
     */
    Visual setDepth( int depth );

    /**
     * <p>Sets where the sprite is rendered. 1 unit is equal to one world unit. Sprites can be set
     * half way between cells. </p>
     * <p>Note: Setting a Visuals location only changes where the user sees the visual. It does not
     * change where the thing that the visual represents is drawn.</p>
     * @param x the x position
     * @param y the y position
     * @return this
     */
    Visual setLocation( float x, float y );

    /**
     * The width of the visual in world units
     * @return the visuals width
     */
    float width();

    /**
     * The height of the visual in world units
     * @return the visuals height
     */
    float height();

    /**
     * Sets the width and height of the visual
     * @param width the visuals width
     * @param height the visuals height
     * @return this
     */
    Visual setSize( float width, float height );

    /**
     * The images x scale
     * @return the images x scale
     */
    float xScale();

    /**
     * The images y scale
     * @return the images y scale
     */
    float yScale();

    /**
     * Sets the x and y scale.
     * @param x the x scale
     * @param y the y scale
     * @return this
     */
    Visual setScale( float x, float y );

    /**
     * Gets the offset that the image will be drawn at
     * @return the x offset
     */
    float xOffset();

    /**
     * Gets the offset the at the image will be drawn at
     * @return the y offset
     */
    float yOffset();

    /**
     * Sets the offset to draw the visual with
     * @param x the x offset
     * @param y the y offset
     * @return this
     */
    Visual setOffset( float x, float y );

    /**
     * Sets the direction that the visual is facing (y positive is up). It is undefined what this
     * actually does, but different visuals can use it for different purposes. Texxel uses this
     * to flip the CharVisuals by modifying the xScale.
     * @param dir the direction to face
     * @return this
     * @throws NullPointerException if dir is null
     */
    Visual setDirection( Point2D dir );

    /**
     * Gets the direction the visual is facing (positive y is up). The returned point does not
     * need to be a "unit vector". See {@link #setDirection(Point2D)} for more info.
     * @return the visuals direction (never null)
     */
    Point2D getDirection();

    /**
     * Gets the rotation of the visual
     * @return the visuals rotation
     */
    float getRotation();

    /**
     * Sets the current rotation of the visual. The rotation is in degrees and counter clockwise
     * @param rotation visuals rotation
     * @return this
     */
    Visual setRotation( float rotation );

    /**
     * Sets the color to use for this sprite.
     * @param color the color to tint the sprite with (in ARGB888 format)
     * @return this
     */
    Visual setColor( int color );

    /**
     * Gets the color that this sprite will drawn with. Changes to the returned sprite will have
     * no effect - however, the returned color should not be altered as the same instance is returned
     * every call.
     * @return the color to draw the sprite with (in ARGB8888 format)
     */
    int getColor();

    /**
     * Updates this sprite.
     * @param delta the time passed since the last call
     */
    void update( float delta );

}
