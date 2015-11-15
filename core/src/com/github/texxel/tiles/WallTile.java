package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class WallTile extends AbstractTile {

    private static final WallTile instance = new WallTile();

    public static WallTile getInstance() {
        return instance;
    }

    private static final Constructor<WallTile> constructor = new Constructor<WallTile>() {
        @Override
        public WallTile newInstance( Bundle bundle ) { return instance; }
    };
    static {
        ConstructorRegistry.put( WallTile.class, constructor );
    }

    @Override
    public String name() {
        return "wall";
    }

    @Override
    public String description() {
        return "Try running into it";
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
    public TextureRegion getDefaultImage() {
        return TileAssets.WALL;
    }
}
