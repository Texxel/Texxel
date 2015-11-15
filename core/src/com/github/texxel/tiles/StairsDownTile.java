package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class StairsDownTile extends StairsTile implements Interactable {

    private static Constructor<StairsDownTile> constructor = new Constructor<StairsDownTile>() {
        @Override
        public StairsDownTile newInstance( Bundle bundle ) { return new StairsDownTile( bundle ); }
    };
    static {
        ConstructorRegistry.put( StairsDownTile.class, constructor );
    }

    public StairsDownTile( Dungeon dungeon, int targetLevel, int x, int y ) {
        super( dungeon, targetLevel, x, y );
    }

    private StairsDownTile( Bundle bundle ) {
        super( bundle );
    }

    @Override
    public String name() {
        return "Stairs";
    }

    @Override
    public String description() {
        return "I wonder whats down there...";
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.STAIRS_DOWN;
    }

}
