package com.github.texxel.levels.components;

import java.util.Collection;

public interface LevelBuilder {

    Collection<Room> buildRooms( int width, int height );
}
