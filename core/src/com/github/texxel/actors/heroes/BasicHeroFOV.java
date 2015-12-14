package com.github.texxel.actors.heroes;

import com.github.texxel.mechanics.BasicFOV;
import com.github.texxel.utils.Point2D;

public class BasicHeroFOV extends BasicFOV implements HeroFOV {

    private static final long serialVersionUID = 2536539835420855944L;

    private final boolean[][] discovered;

    public BasicHeroFOV( boolean[][] solids, Point2D location ) {
        super( solids, location );
        discovered = new boolean[solids.length][solids[0].length];
    }

    @Override
    protected void update() {
        super.update();
        for ( int i = 0; i < width; i++ )
            for ( int j = 0; j < height; j++ )
                if ( isVisible( i, j ) )
                    discovered[i][j] = true;
    }

    /**
     * Tests if a cell has been previously seen by the hero.
     * @param x the cell's x position
     * @param y the cell's y position
     * @return true if the hero has seen the cell before
     * @throws IndexOutOfBoundsException if x,y is outside of the grid
     */
    @Override
    public boolean isDiscovered( int x, int y ) {
        return discovered[x][y];
    }

    /**
     * Sets if the hero has seen a cell. If the cell is set to discovered, then the cell shall
     * remain discovered forever. If set to un-discovered, then the cell shall only be un-discovered
     * until the next time the hero sees the cell. // TODO was that true?
     * @param x the cell's x position
     * @param y the cell's y position
     * @param discovered true if the cell is discovered
     * @throws IndexOutOfBoundsException if x,y is outside the of the grid
     */
    @Override
    public void setDiscovered( int x, int y, boolean discovered ) {
        this.discovered[x][y] = discovered;
    }

}
