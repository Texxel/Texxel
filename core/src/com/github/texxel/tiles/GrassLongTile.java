package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class GrassLongTile extends GrassTile implements Trampleable {

    private static final GrassLongTile instance = new GrassLongTile();
    private static final long serialVersionUID = 8304265039131568620L;

    public static GrassLongTile getInstance() {
        return instance;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.GRASS_LONG;
    }

    @Override
    public Tile onTrample( Object source ) {
        return GrassShortTile.getInstance();
    }

    @Override
    public Tile onLeave( Object source ) {
        return this;
    }

    private Object readResolve() {
        return instance;
    }
}
