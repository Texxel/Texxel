package com.github.texxel.utils;

import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

public class Point2D implements Bundlable {

    private static final Constructor<Point2D> CONSTRUCTOR = new Constructor<Point2D>() {
        @Override
        public Point2D newInstance( Bundle bundle ) {
            int x = bundle.getInt( "x" );
            int y = bundle.getInt( "y" );
            return new Point2D( x, y );
        }

    };
    static {
        ConstructorRegistry.put( Point2D.class, CONSTRUCTOR );
    }

    public static final Point2D
            UP          = new Point2D(  0,  1 ),
            DOWN        = new Point2D(  0, -1 ),
            LEFT        = new Point2D( -1,  0 ),
            RIGHT       = new Point2D(  1,  0 ),
            UP_LEFT     = new Point2D( -1,  1 ),
            UP_RIGHT    = new Point2D(  1,  1 ),
            DOWN_LEFT   = new Point2D( -1, -1 ),
            DOWN_RIGHT  = new Point2D(  1, -1 );

    public final int x;
    public final int y;

    /**
     * Returns a point that points in the closest direction to the components passed.
     * @param x the x part
     * @param y the y part
     * @return a vector that points in the same direction
     */
    public static Point2D direction( float x, float y ) {
        double PION8 = Math.PI/8;
        double angle = Math.atan2( y, x );
        if ( angle >= -PION8 && angle <= PION8 )
            return RIGHT;
        else if ( angle > PION8 && angle < 3*PION8 )
            return UP_RIGHT;
        else if ( angle >= 3*PION8 && angle <= 5*PION8 )
            return UP;
        else if ( angle > 5*PION8 && angle < 7*PION8 )
            return UP_LEFT;

        else if ( angle < -PION8 && angle > -3*PION8 )
            return DOWN_RIGHT;
        else if ( angle <= -3*PION8 && angle >= -5*PION8 )
            return DOWN;
        else if ( angle < -5*PION8 && angle > -7*PION8 )
            return DOWN_LEFT;
        else
            return LEFT;
    }

    public Point2D( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Point2D plus( int x, int y ) {
        return new Point2D( this.x + x, this.y + y );
    }

    public Point2D minus( int x, int y ) {
        return new Point2D( this.x - x, this.y - y );
    }

    public Point2D plus( Point2D other ) {
        return new Point2D( this.x + other.x, this.y + other.y );
    }

    public Point2D minus( Point2D other ) {
        return new Point2D( this.x - other.x, this.y - other.y );
    }

    public Point2D flip() {
        return new Point2D( -this.x, -this.y );
    }

    public boolean equals( int x, int y ) {
        return this.x == x && this.y ==  y;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof Point2D ) {
            Point2D other = (Point2D)obj;
            return other.x == this.x && other.y == this.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 19 + x;
        hashCode = hashCode * 19 + y;
        return hashCode;
    }

    @Override
    public String toString() {
        return "Point: (" + x + "," + y + ')';
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.put( "x", x );
        bundle.put( "x", y );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        // nothing to do
    }

    /**
     * Gets a point that is in the direction of the other point
     * @param other the other point
     * @return the direction to the other point
     */
    public Point2D directionTo( Point2D other ) {
        return Point2D.direction( other.x - x, other.y - y );
    }
}
