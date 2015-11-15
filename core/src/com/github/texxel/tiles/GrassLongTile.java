package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class GrassLongTile extends GrassTile implements Trampleable {

    private static final GrassLongTile instance = new GrassLongTile();

    public static GrassLongTile getInstance() {
        return instance;
    }

    private static Constructor<GrassLongTile> constructor = new Constructor<GrassLongTile>() {
        @Override
        public GrassLongTile newInstance( Bundle bundle ) { return instance; }
    };
    static {
        ConstructorRegistry.put( GrassLongTile.class, constructor );
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
}
