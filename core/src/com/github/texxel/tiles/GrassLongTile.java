package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class GrassLongTile extends AbstractTile implements Trampleable, Flammable {

    private static Constructor<GrassLongTile> constructor = new Constructor<GrassLongTile>() {
        @Override
        public GrassLongTile newInstance( Bundle bundle ) { return TileList.GRASS_LONG; }
    };
    static {
        ConstructorRegistry.put( GrassLongTile.class, constructor );
    }

    @Override
    public boolean onBurn( int x, int y ) {
        return true;
    }

    @Override
    public void onExtinguished( int x, int y ) {
        Dungeon.level().getTileMap().setTile( x, y, TileList.EMBERS );
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
        return TileAssets.GRASS_LONG;
    }

    @Override
    public String name() {
        return "long grass";
    }

    @Override
    public String description() {
        return "Some grass. Apparently no-one down here owns a lawn mower";
    }

    @Override
    public void onTrample( Object source, int x, int y ) {
        Dungeon.level().getTileMap().setTile( x, y, TileList.GRASS_SHORT );
    }

    @Override
    public void onLeave( Object source, int x, int y ) {
    }
}
