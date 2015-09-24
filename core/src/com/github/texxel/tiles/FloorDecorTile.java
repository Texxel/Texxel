package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class FloorDecorTile extends AbstractTile {

    private static Constructor<FloorDecorTile> constructor = new Constructor<FloorDecorTile>() {
        @Override
        public FloorDecorTile newInstance( Bundle bundle ) { return TileList.FLOOR_DECOR; }
    };
    static {
        ConstructorRegistry.put( FloorDecorTile.class, constructor );
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
    public TextureRegion getImage() {
        return TileAssets.FLOOR_SPECIAL;
    }

    @Override
    public String name() {
        return "floor";
    }

    @Override
    public String description() {
        return "The floor";
    }
}
