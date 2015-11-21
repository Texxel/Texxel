package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.tiles.StairsDownTile;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Point2D;

public class ExitRoom extends StairsRoom {

    private static final ExitRoom instance = new ExitRoom();
    public static ExitRoom instance() {
        return instance;
    }

    @Override
    protected Tile getStairs( Level level, Point2D location ) {
        return new StairsDownTile( level.dungeon(), level.getLevelBelow(), location.x, location.y );
    }

}
