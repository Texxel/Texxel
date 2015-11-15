package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class EmbersTile extends AbstractTile {

    private static final EmbersTile instance = new EmbersTile();
    public static EmbersTile getInstance() {
        return instance;
    }

    private static Constructor<EmbersTile> constructor = new Constructor<EmbersTile>() {
        @Override
        public EmbersTile newInstance( Bundle bundle ) { return instance; }
    };
    static {
        ConstructorRegistry.put( EmbersTile.class, constructor );
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.EMBERS;
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
    public String name() {
        return "Embers";
    }

    @Override
    public String description() {
        return "Smouldering remains...";
    }
}
