package com.github.texxel.levels.components;

import com.github.texxel.levels.roomtypes.RoomType;
import com.github.texxel.mechanics.Graph;
import com.github.texxel.mechanics.Graph.Node;
import com.github.texxel.utils.Point2D;
import com.github.texxel.utils.Random;
import com.github.texxel.utils.Rectangle;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Room implements Graph.Node {

    private int distance;
    private int price;
    public final Rectangle bounds;
    private Point2D center;
    public HashSet<Room> neighbours = new HashSet<>();
    public HashMap<Room, Point2D> connected = new HashMap<>();
    public RoomType type;

    public Room ( Rectangle rectangle ) {
        this.bounds = rectangle;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public void setDistance( int value ) {
        distance = value;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice( int value ) {
        price = value;
    }

    @Override
    public Collection<? extends Node> edges() {
        return neighbours;
    }

    public int width() {
        return bounds.width;
    }

    public int height() {
        return bounds.height;
    }

    public void connect( Room room ) {
        if ( !connected.containsKey( room ) ) {
            Rectangle intersection = this.bounds.intersection( room.bounds );
            if ( intersection == null )
                throw new IllegalStateException( "room does not touch this room" );
            int x, y;
            if (intersection.height == 1) {
                x = Random.Int( intersection.x + 1, intersection.x2 - 1 );
                y = intersection.y; // = intersection.y2
            } else {
                x = intersection.x; // = intersection.x2
                y = Random.Int( intersection.y + 1, intersection.y2 - 1 );
            }
            Point2D door = new Point2D( x, y );
            connected.put( room, door );
            room.connected.put( this, door );
        }
    }

    /**
     * Sets the other room to be a neighbour of this one. Will only do something
     * if the rooms have a common wall and a space to place a door
     * @return true if the other room was a valid neighbour
     */
    public boolean addNeighbour( Room other ) {
        Rectangle intersection = this.bounds.intersection( other.bounds );
        if ( intersection != null ) {
            // make sure there's room to add a door
            if (intersection.height > 3 || intersection.width > 3) {
                neighbours.add( other );
                other.neighbours.add( this );
                return true;
            }
        }
        return false;
    }

    public Point2D center( ) {
        Point2D center = this.center;
        if ( center == null )
            return this.center = new Point2D( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2 );
        return center;
    }

}