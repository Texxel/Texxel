package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class GrassShortTile extends GrassTile {

    private static final GrassShortTile INSTANCE = new GrassShortTile();
    private static final long serialVersionUID = -3681911817739185919L;

    public static GrassShortTile getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.GRASS_SHORT;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
