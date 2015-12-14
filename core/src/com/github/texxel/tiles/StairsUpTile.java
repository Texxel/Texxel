package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.Dungeon;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.scenes.IntervalLevelScreen;
import com.github.texxel.sprites.TileAssets;

public class StairsUpTile extends StairsTile implements Interactable {

    private static final long serialVersionUID = -8655260990341680009L;

    public StairsUpTile( Dungeon dungeon, LevelDescriptor targetLevel, int x, int y ) {
        super( dungeon, targetLevel, x, y );
    }

    @Override
    public TextureRegion getDefaultImage() {
        return TileAssets.STAIRS_UP;
    }

    @Override
    public String name() {
        return "stairs";
    }

    @Override
    public String description() {
        return "some stairs. I wonder what's up there";
    }

    @Override
    protected IntervalLevelScreen.TransitionReason transitionReason() {
        return IntervalLevelScreen.StandardTransitionReason.STAIRS_ASCEND;
    }
}
