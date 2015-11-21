package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.tiles.StairsUpTile;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Point2D;

public class EntryRoom extends StairsRoom {

    private static final EntryRoom instance = new EntryRoom();
    public static EntryRoom instance() {
        return instance;
    }

    @Override
    protected Tile getStairs( Level level, Point2D location ) {
        return new StairsUpTile( level.dungeon(), level.getLevelAbove(), location.x, location.y );
    }
}
