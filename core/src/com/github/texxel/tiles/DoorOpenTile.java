package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class DoorOpenTile extends AbstractTile implements Trampleable {

    private static final DoorOpenTile instance = new DoorOpenTile();
    private static final long serialVersionUID = -5210319587033934807L;

    public static DoorOpenTile getInstance() {
        return instance;
    }

    @Override
    public boolean isPassable() {
        return true;
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
    public String name() {
        return "door";
    }

    @Override
    public String description() {
        return "A door";
    }

    @Override
    public Tile onTrample( Object source ) {
        return this;
    }

    @Override
    public Tile onLeave( Object source ) {
        return DoorClosedTile.getInstance();
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.DOOR_OPEN;
    }

    private Object readResolve() {
        return instance;
    }

}
