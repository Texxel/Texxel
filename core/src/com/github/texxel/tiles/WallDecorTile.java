package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class WallDecorTile extends WallTile {

    public static final WallDecorTile instance = new WallDecorTile();

    private static final Constructor<WallDecorTile> constructor = new Constructor<WallDecorTile>() {
        @Override
        public WallDecorTile newInstance( Bundle bundle ) { return instance; }
    };
    static {
        ConstructorRegistry.put( WallDecorTile.class, constructor );
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.WALL_DECORATED;
    }
}
