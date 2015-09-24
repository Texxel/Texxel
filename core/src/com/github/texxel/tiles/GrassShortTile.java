package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class GrassShortTile extends AbstractTile implements Flammable {

    private static Constructor<GrassShortTile> constructor = new Constructor<GrassShortTile>() {
        @Override
        public GrassShortTile newInstance( Bundle bundle ) { return TileList.GRASS_SHORT; }
    };
    static {
        ConstructorRegistry.put( GrassShortTile.class, constructor );
    }

    @Override
    public String name() {
        return "short grass";
    }

    @Override
    public String description() {
        return "Some grass. What did you expect to find?";
    }

    @Override
    public boolean isSolid() {
        return false;
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
    public boolean onBurn( int x, int y ) {
        return true;
    }

    @Override
    public void onExtinguished( int x, int y ) {
        Dungeon.level().getTileMap().setTile( x, y, TileList.EMBERS );
    }

    @Override
    public TextureRegion getImage() {
        return TileAssets.GRASS_SHORT;
    }
}
