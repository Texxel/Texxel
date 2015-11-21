package com.github.texxel.levels.roomtypes;

import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.tiles.DoorClosedTile;
import com.github.texxel.tiles.FloorTile;
import com.github.texxel.utils.Point2D;
import com.github.texxel.utils.Random;
import com.github.texxel.utils.Rectangle;

import java.util.Map;


public class PassageRoom implements RoomType {

    private static final PassageRoom instance = new PassageRoom();
    public static PassageRoom instance() {
        return instance;
    }

    @Override
    public void decorate( Level level, TileMap tileMap, Room room ) {
        Point2D center = room.center();

        Rectangle bounds = room.bounds;
        if (bounds.width > bounds.height
                || ( bounds.width == bounds.height && Random.roll( 2 ) ) ) {
            // passage goes from left to right

            // set left to far right and right to far left
            int left = bounds.x2 - 1;
            int right = bounds.x + 1;

            for (Point2D door : room.connected.values()) {

                // draw passage from door to vertical center
                int stepDirection = door.y < center.y ? +1 : -1;
                if (door.x == bounds.x ) {
                    // inform passage it can extend to far left
                    left = bounds.x + 1;

                    for (int i = door.y; i != center.y; i += stepDirection)
                        tileMap.setTile( left, i, FloorTile.getInstance() );
                } else if ( door.x == bounds.x2 ) {
                    // inform passage it can extend to far right
                    right = bounds.x2 - 1;

                    for (int i = door.y; i != center.y; i += stepDirection)
                        tileMap.setTile( right, i, FloorTile.getInstance() );
                } else {
                    // extend passage way if needed
                    if (door.x < left)
                        left = door.x;
                    if (door.x > right)
                        right = door.x;

                    for ( int i = door.y + stepDirection; i != center.y; i += stepDirection )
                        tileMap.setTile( door.x, i, FloorTile.getInstance() );
                }
            }

            // draw the passage from the left to the right
            // if left never got set to less than right, nothing will be drawn
            for ( int i = left; i <= right; i++ )
                tileMap.setTile( i, center.y, FloorTile.getInstance() );

        } else {
            // passage goes top to bottom (much the same as above)

            int top = bounds.y2 - 1;
            int bottom = bounds.y + 1;

            for (Point2D door : room.connected.values()) {

                int stepDirection = door.x < center.x ? +1 : -1;

                if (door.y == bounds.y ) {
                    top = bounds.y + 1;
                    for ( int i = door.x; i != center.x; i += stepDirection)
                        tileMap.setTile( i, top, FloorTile.getInstance() );
                } else if ( door.y == bounds.y2 ) {
                    bottom = bounds.y2 - 1;
                    for ( int i = door.x; i != center.x; i += stepDirection )
                        tileMap.setTile( i, bottom, FloorTile.getInstance() );
                } else {
                    if (door.y < top)
                        top = door.y;
                    if (door.y > bottom)
                        bottom = door.y;

                    for ( int i = door.x + stepDirection; i != center.x; i += stepDirection )
                        tileMap.setTile( i, door.y, FloorTile.getInstance() );
                }
            }

            for (int i = top; i <= bottom; i++)
                tileMap.setTile( center.x, i, FloorTile.getInstance() );
        }

        for ( Map.Entry<Room, Point2D> entry : room.connected.entrySet()) {
            Room connected = entry.getKey();
            Point2D door = entry.getValue();
            if (connected.type instanceof PassageRoom ) {
                tileMap.setTile( door.x, door.y, FloorTile.getInstance() );
            } else
                tileMap.setTile( door.x, door.y, DoorClosedTile.getInstance() );
        }
    }

}