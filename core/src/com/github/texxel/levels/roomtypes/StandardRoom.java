package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileFiller;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.tiles.DoorClosedTile;
import com.github.texxel.tiles.FloorTile;
import com.github.texxel.tiles.Tile;
import com.github.texxel.tiles.WallTile;
import com.github.texxel.utils.Point2D;

public class StandardRoom implements RoomType {

    private static final StandardRoom instance = new StandardRoom();
    public static StandardRoom instance() {
        return instance;
    }

    @Override
    public void decorate( Level level, TileMap tileMap, Room room ) {
        new TileFiller.Border( room.bounds ) {
            @Override
            public Tile makeInnerTile( int x, int y ) {
                return FloorTile.getInstance();
            }
            @Override
            public Tile makeOuterTile( int x, int y ) {
                return WallTile.getInstance();
            }
        }.paint( tileMap );

        for (Point2D door : room.connected.values()) {
            tileMap.setTile( door.x, door.y, DoorClosedTile.getInstance() );
        }
    }

}