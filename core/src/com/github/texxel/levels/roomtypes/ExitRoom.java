package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileFiller;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.tiles.DoorClosedTile;
import com.github.texxel.tiles.FloorTile;
import com.github.texxel.tiles.Tile;
import com.github.texxel.tiles.WallDecorTile;
import com.github.texxel.tiles.WallTile;
import com.github.texxel.utils.Point2D;

public class ExitRoom implements RoomType {

    @Override
    public void decorate( TileMap tileMap, Room room ) {

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

        for ( Point2D door : room.connected.values() ) {
            tileMap.setTile( door.x, door.y, DoorClosedTile.getInstance() );
        }

        Point2D center = room.center();
        tileMap.setTile( center.x, center.y, WallDecorTile.getInstance() );
    }

}
