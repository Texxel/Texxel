package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.TileAssets;

public class DoorClosedTile extends AbstractTile implements Trampleable {

    private static final long serialVersionUID = -985997027086983197L;

    private static final DoorClosedTile instance = new DoorClosedTile();

    public static DoorClosedTile getInstance() {
        return instance;
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
        return true;
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.DOOR_CLOSED;
    }

    @Override
    public String name() {
        return "A door";
    }

    @Override
    public String description() {
        return "One of those thingies with the handle and the frame";
    }

    @Override
    public Tile onTrample( Object source ) {
        return DoorOpenTile.getInstance();
    }

    @Override
    public Tile onLeave( Object source ) {
        return this;
    }

    private Object readResolve() {
        return instance;
    }
}
