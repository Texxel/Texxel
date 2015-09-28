package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;
import com.github.texxel.utils.Point2D;

public class DoorLockedTile extends AbstractTile implements Interactable {

    private static Constructor<DoorLockedTile> constructor = new Constructor<DoorLockedTile>() {
        @Override
        public DoorLockedTile newInstance( Bundle bundle ) { return TileList.DOOR_LOCKED; }
    };
    static {
        ConstructorRegistry.put( DoorLockedTile.class, constructor );
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
    public TextureRegion getImage() {
        return TileAssets.DOOR_LOCKED;
    }

    @Override
    public String name() {
        return "Locked door";
    }

    @Override
    public String description() {
        return "A locked door. There should be a key around here somewhere...";
    }

    @Override
    public void interact( Char ch, int x, int y ) {
        ch.spend( 3 );
        Dungeon.level().getTileMap().setTile( x, y, TileList.DOOR_CLOSED );
    }

    @Override
    public boolean canInteract( Char ch, int x, int y ) {
        Point2D p = ch.getLocation();
        return PathFinder.isNextTo( x, y, p.x, p.y );
    }
}
