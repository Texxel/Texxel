package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.tiles.StairsUpTile;
import com.github.texxel.tiles.Tile;

public class EntryRoom extends StairsRoom {

    private static final EntryRoom instance = new EntryRoom();
    public static EntryRoom instance() {
        return instance;
    }

    @Override
    protected Tile getStairs( Level level ) {
        return new StairsUpTile( level.getLevelAbove() );
    }
}
