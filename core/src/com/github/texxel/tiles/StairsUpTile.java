package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.sprites.TileAssets;

public class StairsUpTile extends AbstractTile implements Interactable {

    private static Constructor<StairsUpTile> constructor = new Constructor<StairsUpTile>() {
        @Override
        public StairsUpTile newInstance( Bundle bundle ) { return TileList.STAIRS_UP; }
    };
    static {
        ConstructorRegistry.put( StairsUpTile.class, constructor );
    }

    @Override
    public TextureRegion getImage() {
        return TileAssets.STAIRS_UP;
    }

    @Override
    public void interact( Char ch, int x, int y ) {
        Dungeon.goTo( Dungeon.level().id()+1 );
    }

    @Override
    public boolean canInteract( Char ch, int x, int y ) {
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
    public boolean isPassable() {
        return true;
    }

    @Override
    public String name() {
        return "stairs";
    }

    @Override
    public String description() {
        return "some stairs. I wonder what's up there";
    }

}
