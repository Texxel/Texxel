package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class FloorDecorTile extends FloorTile {

    private static final FloorDecorTile instance = new FloorDecorTile();
    public static FloorDecorTile getInstance() {
        return instance;
    }

    private static Constructor<FloorDecorTile> constructor = new Constructor<FloorDecorTile>() {
        @Override
        public FloorDecorTile newInstance( Bundle bundle ) { return instance; }
    };
    static {
        ConstructorRegistry.put( FloorDecorTile.class, constructor );
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.FLOOR_SPECIAL;
    }

}
