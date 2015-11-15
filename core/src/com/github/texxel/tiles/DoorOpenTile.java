package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class DoorOpenTile extends AbstractTile implements Trampleable {

    private static final DoorOpenTile instance = new DoorOpenTile();

    public static DoorOpenTile getInstance() {
        return instance;
    }

    private static Constructor<DoorOpenTile> constructor = new Constructor<DoorOpenTile>() {
        @Override
        public DoorOpenTile newInstance( Bundle bundle ) { return instance; }
    };
    static {
        ConstructorRegistry.put( DoorOpenTile.class, constructor );
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

}
