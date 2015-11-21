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

/**
 * The stairs room is a room designed to have a tile that leads to somewhere else. While it is
 * called a "stairs" room, subclasses can configure the type of stairs it uses
 */
public abstract class StairsRoom implements RoomType {

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

        for ( Point2D door : room.connected.values() ) {
            tileMap.setTile( door.x, door.y, DoorClosedTile.getInstance() );
        }

        Point2D center = getStairsLocation( room );
        int x = center.x;
        int y = center.y;
        Tile stairs = getStairs( level, center );
        tileMap.setTile( x, y, stairs );
    }

    /**
     * Gets the location of the stairs tile. By default, this is the center of the room. The returned
     * location will then be passed to the the {@link #getStairs(Level, Point2D)}
     * method.
     * @param room the room that the stairs is.
     * @return the stairs location. Never null.
     */
    protected Point2D getStairsLocation( Room room ) {
        return room.center();
    }

    /**
     * Gets the tile that will be used as the stairs.
     * @param level the level the stairs are going to be placed into
     * @param location the place the returned tile will be placed in the map
     * @return a new Tile. Never null
     */
    protected abstract Tile getStairs( Level level, Point2D location );

}
