package com.github.texxel.levels.components;

import com.github.texxel.levels.Level;

import java.util.Collection;

public class BasicDecorator implements LevelDecorator {

    @Override
    public void decorate( Level level, Collection<Room> rooms ) {
        // TODO better level decoration
        for ( Room room : rooms ) {
            room.type.decorate( level.getTileMap(), room );
        }
    }

}
