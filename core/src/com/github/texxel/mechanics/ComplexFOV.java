package com.github.texxel.mechanics;

import com.github.texxel.utils.Point2D;

public class ComplexFOV extends BasicFOV implements FieldOfVision {

    private static final long serialVersionUID = 2536539835420855944L;

    private final boolean[][] discovered;

    public ComplexFOV( boolean[][] solids, Point2D location ) {
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

    @Override
    public boolean isKnown( int x, int y ) {
        return discovered[x][y];
    }

    @Override
    public void setKnown( int x, int y, boolean discovered ) {
        this.discovered[x][y] = discovered;
    }

}
