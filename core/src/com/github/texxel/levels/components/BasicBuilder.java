package com.github.texxel.levels.components;

import com.badlogic.gdx.Gdx;
import com.github.texxel.levels.roomtypes.BlankRoom;
import com.github.texxel.levels.roomtypes.EntryRoom;
import com.github.texxel.levels.roomtypes.ExitRoom;
import com.github.texxel.levels.roomtypes.PassageRoom;
import com.github.texxel.levels.roomtypes.StandardRoom;
import com.github.texxel.mechanics.Graph;
import com.github.texxel.utils.Random;
import com.github.texxel.utils.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasicBuilder implements LevelBuilder {

    /**
     * The absolutely minimum size room that can be generated (must be less than half of minRoomSize)
     */
    private static final int absoluteMinSize = 4;
    /**
     * Most rooms will be bigger than this in both direction
     */
    private static final int minRoomSize = 8;
    /**
     * Most rooms will be smaller than this in both direction
     */
    private static final int maxRoomSize = 10;
    /**
     * The minimum amount of rectangles that might get turned into rooms.
     */
    private static final int minSplitRectangles = 8;

    /**
     * A list of all the rooms that get generated. Many rooms are not used
     */
    private final ArrayList<Room> allRooms = new ArrayList<>();
    /**
     * A list of all the connected rooms. All rooms in this list will be accessible to the Hero
     */
    private ArrayList<Room> connectedRooms = new ArrayList<>();

    /**
     * The entrance room
     */
    private Room roomEntrance = null;
    /**
     * The exit room
     */
    private Room roomExit = null;

    @Override
    public Collection<Room> planRooms( int width, int height ) {
        Gdx.app.log( "RegularLevel", "Clearing the level" );
        // flush all the old data out
        clear();

        ArrayList<Rectangle> rectangles = new ArrayList<>();

        Gdx.app.log( "RegularLevel", "Split level" );
        // Split the world up into little room
        splitBoundaries( Rectangle.fromSize( 0, 0, width, height ), rectangles );

        Gdx.app.log( "RegularLevel", "Check amount of rooms" );
        // Check there's a decent amount of rooms
        if ( rectangles.size() < minSplitRectangles ) {
            return null;
        }

        for ( Rectangle rectangle : rectangles ) {
            allRooms.add( new Room( rectangle ) );
        }

        Gdx.app.log( "RegularLevel", "Set price" );
        Graph.setPrice( allRooms, 1 );

        Gdx.app.log( "RegularLevel", "Get rooms next to each other" );
        // find which rooms are next to each other
        int size = allRooms.size();
        for ( int i = 0; i < size - 1; i++ ) {
            for ( int j = i + 1; j < size; j++ ) {
                allRooms.get( i ).addNeighbour( allRooms.get( j ) );
            }
        }
        Gdx.app.log( "RegularLevel", "Set entrance/exits" );
        // Set the entrance and exit rooms
        if (!setEntryAndExit())
            return null;

        Gdx.app.log( "RegularLevel", "Path forge" );
        // build path from entrance to exit
        if (!pathForge())
            return null;

        Gdx.app.log( "RegularLevel", "Tack on dead ends" );
        // tack some dead end rooms on
        if (!connectRooms())
            return null;

        Gdx.app.log( "RegularLevel", "Assign room types" );
        assignRoomTypes();

        Gdx.app.log( "RegularLevel", "Finished" );
        return connectedRooms;
    }

    /**
     * Clears all the levels data for a fresh (re)build. This only clears the data associated with
     * the actual level structure. Items like known actors remain unchanged
     */
    protected void clear() {
        allRooms.clear();
        connectedRooms.clear();
        roomEntrance = null;
        roomExit = null;
    }

    /**
     * Splits a rectangle into lots of little divisions. Room sizes can be configured with {@link #minRoomSize}, {@link #maxRoomSize} and {@link #absoluteMinSize}.
     * @param r the rectangle to split up
     * @param boundaries a list to add all the split up rectangles into
     */
    protected void splitBoundaries( Rectangle r, ArrayList<Rectangle> boundaries ) {
        int roomWidth = r.width;
        int roomHeight = r.height;

        if (roomHeight <= minRoomSize && roomWidth > maxRoomSize ) {
            // A long flat room. Split vertically
            int middle = Random.Int( r.x + absoluteMinSize, r.x2 - absoluteMinSize );
            Rectangle leftSection = Rectangle.fromBounds( r.x, r.y, middle, r.y2 );
            Rectangle rightSection = Rectangle.fromBounds( middle, r.y, r.x2, r.y2 );
            splitBoundaries( leftSection, boundaries );
            splitBoundaries( rightSection, boundaries );
        } else if ( roomHeight > maxRoomSize && roomWidth <= minRoomSize ) {
            // A tall skinny room. Split horizontally
            int middle = Random.Int( r.y + absoluteMinSize, r.y2 - absoluteMinSize );
            Rectangle topSection = Rectangle.fromBounds( r.x, r.y, r.x2, middle );
            Rectangle botSection = Rectangle.fromBounds( r.x, middle, r.x2, r.y2 );
            splitBoundaries( topSection, boundaries );
            splitBoundaries( botSection, boundaries );

        } else if ( roomWidth <= minRoomSize || roomHeight <= minRoomSize ) {
            // room is small enough to add
            boundaries.add( r );

        } else if (roomWidth <= maxRoomSize
                && roomHeight <= maxRoomSize
                && Math.random() <= (float)(minRoomSize * minRoomSize) / r.area() ) {
            // rooms of average size, let it have a fair chance of being added
            boundaries.add( r );

        } else {
            // Split room again (preferring to split in the long direction)
            if ( Random.Float( roomWidth ) > Random.Float( roomHeight ) ) {
                // split vertically
                int middle = Random.Int( r.x + absoluteMinSize, r.x2 - absoluteMinSize );
                Rectangle leftSection = Rectangle.fromBounds( r.x, r.y, middle, r.y2 );
                Rectangle rightSection = Rectangle.fromBounds( middle, r.y, r.x2, r.y2 );
                splitBoundaries( leftSection, boundaries );
                splitBoundaries( rightSection, boundaries );
            } else {
                // split horizontally
                int middle = Random.Int( r.y + absoluteMinSize, r.y2 - absoluteMinSize );
                Rectangle topSection = Rectangle.fromBounds( r.x, r.y, r.x2, middle );
                Rectangle botSection = Rectangle.fromBounds( r.x, middle, r.x2, r.y2 );
                splitBoundaries( topSection, boundaries );
                splitBoundaries( botSection, boundaries );
            }
        }
    }

    /**
     * Sets which rooms are the entrance and exit rooms.
     * If no valid room is found, a full rebuild will be performed.
     */
    protected boolean setEntryAndExit() {
        int distance;
        int retry = 0;
        int minDistance = (int)Math.sqrt( allRooms.size() );
        do {
            if (retry++ > 10) {
                // can't actually build the map properly. Go for a full rebuild
                return false;
            }
            do {
                roomEntrance = Random.select( allRooms );
            } while (roomEntrance.width() < 5 || roomEntrance.height() < 5);

            do {
                roomExit = Random.select( allRooms );
            } while (roomExit == roomEntrance || roomExit.width() < 5 || roomExit.height() < 5);

            Graph.buildDistanceMap( allRooms, roomExit );
            distance = roomEntrance.getDistance();
        } while (distance < minDistance);

        roomEntrance.type = new EntryRoom();
        roomExit.type = new ExitRoom();
        return true;
    }

    /**
     * Forge paths from the entrance to the exit
     * @return false if a rebuild should happen
     */
    protected boolean pathForge() {
        connectedRooms.clear();
        connectedRooms.add( roomEntrance );

        // Build first path from entrance to exit
        Graph.buildDistanceMap( allRooms, roomExit );
        List<Room> path = Graph.buildPath( allRooms, roomEntrance, roomExit );

        Room room = roomEntrance;
        for ( Room next : path) {
            room.connect( next );
            room = next;
            connectedRooms.add( room );
        }

        // Build path to exit again but avoiding last path
        Graph.setPrice( path, roomEntrance.getDistance() );

        Graph.buildDistanceMap( allRooms, roomExit );
        path = Graph.buildPath( allRooms, roomEntrance, roomExit );

        room = roomEntrance;
        for ( Room next : path) {
            room.connect( next );
            room = next;
            connectedRooms.add( room );
        }

        return true;
    }

    /**
     * Connects a bunch of rooms together. These rooms are tacked onto the main path
     */
    protected boolean connectRooms() {
        int mustConnected = (int)(allRooms.size() * Random.Float( 0.5f, 0.7f ));
        while (connectedRooms.size() < mustConnected) {

            Room connectedRoom = Random.select( connectedRooms );
            Room anotherRoom = Random.select( connectedRoom.neighbours );
            if (!connectedRooms.contains( anotherRoom )) {
                connectedRoom.connect( anotherRoom );
                connectedRooms.add( anotherRoom );
            }
        }

        return true;
    }

    /**
     * Assigns all the room types (excluding the entrance and exit rooms which was done earlier)
     */
    protected void assignRoomTypes() {
        int count = 0;
        for ( Room room : connectedRooms ) {
            if ( room.type == null ) {
                int connections = room.connected.size();
                if ( connections == 0 ) {
                    room.type = new BlankRoom();
                } else if ( Random.roll( connections * connections ) ) {
                    room.type = new StandardRoom();
                    count++;
                } else {
                    room.type = new PassageRoom();
                }
            }
        }

        /*
        while ( count < 4 ) {
            Room room = randomRoom( paintersList.tunnel );
            if ( room != null ) {
                room.type = new StandardRoom( tileMap );
                count++;
            }
        }
        */
    }

}
