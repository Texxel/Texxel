package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileMap;

/**
 * A generic room, just four walls and a floor.
 */
public class BlankRoom implements RoomType {

    private static final BlankRoom instance = new BlankRoom();
    public static BlankRoom instance() {
        return instance;
    }

    @Override
    public void decorate( Level level, TileMap tileMap, Room room ) {
    }

}
