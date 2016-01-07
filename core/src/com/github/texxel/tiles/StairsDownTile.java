package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.scenes.IntervalLevelScreen;
import com.github.texxel.sprites.TileAssets;

public class StairsDownTile extends StairsTile implements Interactable {

    private static final long serialVersionUID = -8665801635157719028L;

    public StairsDownTile( Dungeon dungeon, LevelDescriptor targetLevel, int x, int y ) {
        super( dungeon, targetLevel, x, y );
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
