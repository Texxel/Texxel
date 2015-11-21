package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileMap;

public interface RoomType {

    void decorate( Level level, TileMap map, Room room );

}
