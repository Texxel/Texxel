package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class GrassShortTile extends GrassTile {

    private static final GrassShortTile INSTANCE = new GrassShortTile();
    public static GrassShortTile getInstance() {
        return INSTANCE;
    }

    private static Constructor<GrassShortTile> constructor = new Constructor<GrassShortTile>() {
        @Override
        public GrassShortTile newInstance( Bundle bundle ) { return INSTANCE; }
    };
    static {
        ConstructorRegistry.put( GrassShortTile.class, constructor );
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.GRASS_SHORT;
    }
}
