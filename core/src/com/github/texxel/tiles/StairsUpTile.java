package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class StairsUpTile extends StairsTile {

    private static final long serialVersionUID = -8655260990341680009L;

    public StairsUpTile( int targetLevel ) {
        super( targetLevel );
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.STAIRS_UP;
    }

    @Override
    public String name() {
        return "stairs";
    }

    @Override
    public String description() {
        return "some stairs. I wonder what's up there";
    }

}
