package com.github.texxel.levels.components;

import com.github.texxel.levels.Level;

import java.util.Collection;

public class BasicDecorator implements LevelDecorator {

    private static final long serialVersionUID = 6389044897194884080L;

    @Override
    public void decorate( Level level, Collection<Room> rooms ) {
        // TODO better level decoration
        for ( Room room : rooms ) {
            room.type.decorate( level, level.getTileMap(), room );
        }
    }

}
