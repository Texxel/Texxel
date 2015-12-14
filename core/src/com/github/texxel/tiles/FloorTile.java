package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class FloorTile extends AbstractTile {

    private static final FloorTile INSTANCE = new FloorTile();
    private static final long serialVersionUID = 3049942547681341863L;

    public static FloorTile getInstance() {
        return INSTANCE;
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

    private Object readResolve() {
        return INSTANCE;
    }

}
