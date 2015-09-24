package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class StairsDownTile extends AbstractTile implements Trampleable {

    private static Constructor<StairsDownTile> constructor = new Constructor<StairsDownTile>() {
        @Override
        public StairsDownTile newInstance( Bundle bundle ) { return TileList.STAIRS_DOWN; }
    };
    static {
        ConstructorRegistry.put( StairsDownTile.class, constructor );
    }

    @Override
    public void onTrample( Object source, int x, int y ) {
        if ( source instanceof Hero )
            Dungeon.goTo( Dungeon.level().id() + 1 );
    }

    @Override
    public void onLeave( Object source, int x, int y ) {

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
    public TextureRegion getImage() {
        return TileAssets.STAIRS_DOWN;
    }

    @Override
    public String name() {
        return "Stairs";
    }

    @Override
    public String description() {
        return "I wonder whats down there...";
    }
}
