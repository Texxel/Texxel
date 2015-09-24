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

    public final int x;
    public final int y;

    public Point2D( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Point2D add( int x, int y ) {
        return new Point2D( this.x + x, this.y + y );
    }

    public Point2D subtract( int x, int y ) {
        return new Point2D( this.x - x, this.y - y );
    }

    public Point2D add( Point2D other ) {
        return new Point2D( this.x + other.x, this.y + other.y );
    }

    public Point2D subtract( Point2D other ) {
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
}
