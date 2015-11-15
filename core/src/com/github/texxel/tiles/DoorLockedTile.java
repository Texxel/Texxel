package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.actors.Char;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;
import com.github.texxel.utils.Point2D;

public class DoorLockedTile extends AdvancedTile implements Interactable {

    private static Constructor<DoorLockedTile> constructor = new Constructor<DoorLockedTile>() {
        @Override
        public DoorLockedTile newInstance( Bundle bundle ) { return new DoorLockedTile( bundle ); }
    };
    static {
        ConstructorRegistry.put( DoorLockedTile.class, constructor );
    }

    public DoorLockedTile( TileMap tileMap, int x, int y ) {
        super( tileMap, x, y );
    }

    protected DoorLockedTile( Bundle bundle ) {
        super( bundle );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        return super.bundle( topLevel );
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
    public void interact( Char ch ) {
        ch.spend( 3 );
        tileMap().setTile( x(), y(), DoorClosedTile.getInstance() );
    }

    @Override
    public boolean canInteract( Char ch ) {
        Point2D p = ch.getLocation();
        return PathFinder.isNextTo( x(), y(), p.x, p.y );
    }
}
