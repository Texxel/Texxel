package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class DoorOpenTile extends AbstractTile implements Trampleable {

    private static Constructor<DoorOpenTile> constructor = new Constructor<DoorOpenTile>() {
        @Override
        public DoorOpenTile newInstance( Bundle bundle ) { return TileList.DOOR_OPEN; }
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
    public void onTrample( Object source, int x, int y ) {
    }

    @Override
    public void onLeave( Object source, int x, int y ) {
        if ( source instanceof Char )
            Dungeon.level().getTileMap().setTile( x, y, TileList.DOOR_CLOSED );
    }

    @Override
    public TextureRegion getImage() {
        return TileAssets.DOOR_OPEN;
    }

}
