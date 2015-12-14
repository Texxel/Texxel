package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class WallTile extends AbstractTile {

    private static final WallTile instance = new WallTile();
    private static final long serialVersionUID = -3101363580956776776L;

    public static WallTile getInstance() {
        return instance;
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

    private Object readResolve() {
        return instance;
    }
}
