package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class DoorClosedTile extends AbstractTile implements Trampleable {

    private static Constructor<DoorClosedTile> constructor = new Constructor<DoorClosedTile>() {
        @Override
        public DoorClosedTile newInstance( Bundle bundle ) { return TileList.DOOR_CLOSED; }
    };
    static {
        ConstructorRegistry.put( DoorClosedTile.class, constructor );
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
    public TextureRegion getImage() {
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
    public void onTrample( Object source, int x, int y ) {
        Dungeon.level().getTileMap().setTile( x, y, TileList.DOOR_OPEN );
    }

    @Override
    public void onLeave( Object source, int x, int y ) {
    }
}
