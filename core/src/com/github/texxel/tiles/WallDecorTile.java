package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class WallDecorTile extends WallTile {

    public static final WallDecorTile instance = new WallDecorTile();
    private static final long serialVersionUID = 1015215794334244613L;

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.WALL_DECORATED;
    }

    private Object readResolve() {
        return instance;
    }
}
