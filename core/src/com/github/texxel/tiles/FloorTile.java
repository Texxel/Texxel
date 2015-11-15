package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class FloorTile extends AbstractTile {

    private static final FloorTile INSTANCE = new FloorTile();
    public static FloorTile getInstance() {
        return INSTANCE;
    }

    private static Constructor<FloorTile> constructor = new Constructor<FloorTile>() {
        @Override
        public FloorTile newInstance( Bundle bundle ) { return INSTANCE; }
    };
    static {
        ConstructorRegistry.put( FloorTile.class, constructor );
    }

    @Override
    public String name() {
        return "floor";
    }

    @Override
    public String description() {
        return "The floor is that thing that people stand on. You should try it some time.";
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.FLOOR;
    }

}
