package com.github.texxel.mechanics;

import com.github.texxel.utils.Point2D;

/**
 * The FieldOfVision class is designed to determine where a character can see to in the world. It is
 * up to the implementation to decide how line of sight is calculated; there is <b>no</b>
 * requirement that if A can see B, then B can see A. All methods will throw an {@link
 * IndexOutOfBoundsException} if it is passed an argument referencing a cell outside of the
 */
public interface FieldOfVision {

    /**
     * Tests if the viewer can see a location
     * @param x the x location
     * @param y the y location
     * @return true if the location can be seen
     */
    boolean isVisible( int x, int y );

    /**
     * A convenience method. The exact same as {@code isVisible( point.x, point.y }
     * @param point the location to test for visibility
     * @return true if the point is visible
     */
    boolean isVisible( Point2D point );

    /**
     * Sets where the viewer is standing
     * @param location where the viewer is
     * @throws NullPointerException if location is null
     */
    void setLocation( Point2D location );

    /**
     * Gets the point the the viewer is looking from
     * @return the viewer's location
     */
    Point2D getLocation();

    /**
     * Tests if a location cannot be seen past
     * @param x the x location
     * @param y the y location
     * @return true if the cell is solid
     */
    boolean isSolid( int x, int y );

    /**
     * Sets if a location cannot be seen through
     * @param x the x location
     * @param y the y location
     * @param solid true if the location is solid
     */
    void setSolid( int x, int y, boolean solid );

    /**
     * Gets the maximum distance that the character can see
     * @return the max view distance
     */
    int getViewDistance();

    /**
     * Sets the maximum distance that the viewer can see. If {@code maxDistance} is set to 0, then
     * the character will only be able to see their own square
     * @param maxDistance the max distance.
     */
    void setViewDistance( int maxDistance );

}
