package com.github.texxel.actors.heroes;

import com.github.texxel.mechanics.FieldOfVision;

public interface HeroFOV extends FieldOfVision {

    /**
     * Tests if a cell has been previously seen by the hero.
     * @param x the cell's x position
     * @param y the cell's y position
     * @return true if the hero has seen the cell before
     * @throws IndexOutOfBoundsException if x,y is outside of the grid
     */
    boolean isDiscovered( int x, int y );

    /**
     * Sets if the hero has seen a cell. If the cell is set to discovered, then the cell shall
     * remain discovered forever. If set to un-discovered, then the cell shall only be un-discovered
     * until the next time the hero sees the cell. // TODO was that true?
     * @param x the cell's x position
     * @param y the cell's y position
     * @param discovered true if the cell is discovered
     * @throws IndexOutOfBoundsException if x,y is outside the of the grid
     */
    void setDiscovered( int x, int y, boolean discovered );

}
