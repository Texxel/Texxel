package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class EmbersTile extends AbstractTile {

    private static final EmbersTile instance = new EmbersTile();
    private static final long serialVersionUID = -6232608345171181970L;

    public static EmbersTile getInstance() {
        return instance;
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

    private Object readResolve() {
        return instance;
    }
}
