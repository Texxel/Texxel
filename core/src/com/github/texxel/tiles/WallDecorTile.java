package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class WallDecorTile extends AbstractTile {

    private static Constructor<WallDecorTile> constructor = new Constructor<WallDecorTile>() {
        @Override
        public WallDecorTile newInstance( Bundle bundle ) { return TileList.WALL_DECOR; }
    };
    static {
        ConstructorRegistry.put( WallDecorTile.class, constructor );
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public TextureRegion getImage() {
        return TileAssets.WALL_DECORATED;
    }

    @Override
    public String name() {
        return "Wall";
    }

    @Override
    public String description() {
        return "A wall. Try running into it :P";
    }
}
