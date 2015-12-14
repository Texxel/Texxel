package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class FloorDecorTile extends FloorTile {

    private static final FloorDecorTile instance = new FloorDecorTile();
    private static final long serialVersionUID = -6784153558096704168L;

    public static FloorDecorTile getInstance() {
        return instance;
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.FLOOR_SPECIAL;
    }

    private Object readResolve() {
        return instance;
    }
}
