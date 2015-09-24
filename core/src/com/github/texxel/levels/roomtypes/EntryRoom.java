package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileFiller;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.tiles.Tile;
import com.github.texxel.tiles.TileList;
import com.github.texxel.utils.Point2D;

public class EntryRoom implements RoomType {

    @Override
    public void decorate( TileMap tileMap, Room room ) {

        new TileFiller.Border( room.bounds ) {
            @Override
            public Tile makeInnerTile( int x, int y ) {
                return TileList.FLOOR;
            }

            @Override
            public Tile makeOuterTile( int x, int y ) {
                return TileList.WALL;
            }
        }.paint( tileMap );

        for ( Point2D door : room.connected.values() ) {
            tileMap.setTile( door.x, door.y, TileList.DOOR_CLOSED );
        }

        Point2D center = room.center();
        tileMap.setTile( center.x, center.y, TileList.STAIRS_UP );
    }

}
