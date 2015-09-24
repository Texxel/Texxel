package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileMap;

public interface RoomType {

    void decorate( TileMap map, Room room );

}
