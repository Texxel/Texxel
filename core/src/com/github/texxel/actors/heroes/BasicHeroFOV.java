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

    @Override
    public boolean isDiscovered( int x, int y ) {
        return discovered[x][y];
    }

    @Override
    public void setDiscovered( int x, int y, boolean discovered ) {
        this.discovered[x][y] = discovered;
    }

}
