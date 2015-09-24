package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileMap;

/**
 * A generic room, just four walls and a floor.
 */
public class BlankRoom implements RoomType {

    @Override
    public void decorate( TileMap tileMap, Room room ) {
    }

}
