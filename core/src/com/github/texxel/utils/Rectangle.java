package com.github.texxel.utils;

import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

/**
 * An immutable representation of a rectangle where all corners are at integer values. The rectangle
 * uses a y-up co-ordinate system, thus, the y2 edge is always smaller than the y edge.
 * Standards dictate that the x and y2 edges (the edges with the smaller values) are included
 * in the rectangle but the x2 and y edges are not included.
 */
public class Rectangle implements Bundlable {

    private static final Constructor<Rectangle> CONSTRUCTOR = new Constructor<Rectangle>() {
        @Override
        public Rectangle newInstance( Bundle bundle ) {
            int x = bundle.getInt( "x" );
            int y = bundle.getInt( "y" );
            int x2 = bundle.getInt( "x2" );
            int y2 = bundle.getInt( "y2" );
            return Rectangle.fromBounds( x, y, x2, y2 );
        }
    };
    static {
        ConstructorRegistry.put( Rectangle.class, CONSTRUCTOR );
    }

    public static Rectangle fromSize( int x, int y, int width, int height ) {
        return new Rectangle( x, y, x + width - 1, y + height - 1, width, height );
    }

    public static Rectangle fromBounds( int x, int y, int x2, int y2 ) {
        return new Rectangle( x, y, x2, y2, x2 - x + 1, y2 - y + 1 );
    }

    /**
     * The bounds of the rectangle
     */
    public final int x, y, x2, y2;
    /**
     * The size of the rectangle
     */
    public final int width, height;

    private Rectangle( int x, int y, int x2, int y2, int width, int height ) {
        if ( height <= 0 )
            throw new IllegalArgumentException( "y cannot be smaller than y2. " +
                    "Passed y=" + y + ", y2=" + y2 );
        if ( width <= 0 )
            throw new IllegalArgumentException( "x cannot be smaller than x2. Passed x=" + x + ", x2=" + x2 );
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
        this.height = height;
    }

    /**
     * The area of the rectangle. The edges of the rectangle are included.
     * @return the area's rectangle
     */
    public int area() {
        return width*height;
    }

    /**
     * Calculates the intersection of the two rectangles. If no intersection is found, then null
     * is returned. The returned rectangle will have its y-axis system in the same system as this
     * rectangle.
     * @param other the other rectangle to compute the intersection with
     * @return the intersected rectangle. null if there as no intersection.
     * @throws NullPointerException if the other rectangle is null
     */
    public Rectangle intersection( Rectangle other ) {
        // get x/x2 values
        final int x = Math.max( this.x, other.x );
        final int x2 = Math.min( this.x2, other.x2 );
        if ( x > x2 )
            return null;

        // get y/y2 values
        final int y = Math.max( other.y, this.y );
        final int y2 = Math.min( other.y2, this.y2 );
        if ( y > y2 )
            return null;

        return Rectangle.fromBounds( x, y, x2, y2 );
    }

    /**
     * Calculates the smallest rectangle that encompasses both rectangles. The returned rectangle
     * will have its y-axis in the same orientation as this rectangle
     * @param other the other rectangle to compute the bounds with
     * @return the bounding rectangle
     */
    public Rectangle bounds( Rectangle other ) {
        // get x/x2 values
        int x = Math.min( this.x, other.x );
        int x2 = Math.max( this.x2, other.x2 );

        // get y/y2 values
        final int y = Math.min( other.y, this.y );
        final int y2 = Math.max( other.y2, this.y2 );

        return Rectangle.fromBounds( x, y, x2, y2 );
    }

    /**
     * Moves all edges closer to the center by an amount.
     * @param amount the amount to move the edges in by
     * @return the shrunk rectangle. Negative amounts will expand the rectangle.
     * @throws IllegalArgumentException if the rectangle is shrunk pass 0 width/height
     */
    public Rectangle shrunk( int amount ) {
        int x = this.x + amount;
        int x2 = this.x2 - amount;
        int y = this.y + amount;
        int y2 = this.y2 - amount;
        try {
            return Rectangle.fromBounds( x, y, x2, y2 );
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException( "Rectangle shrunk too much!", e );
        }
    }

    /**
     * Moves all edges away from the center an amount.
     * @param amount the amount to move the edges out by
     * @return the expanded rectangle. Negative amounts will shrink the rectangle.
     * @throws IllegalArgumentException if the rectangle is shrunk pass 0 width/height
     */
    public Rectangle expanded( int amount ) {
        return shrunk( -amount );
    }

    @Override
    public String toString() {
        return "Rectangle: x=" + x + ",y=" + y + ",x2=" + x2 + ",y2=" + y2;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( !(obj instanceof Rectangle) )
            return false;
        Rectangle other = (Rectangle)obj;
        return other.x == this.x && other.y == this.y
                && other.x2 == this.x2 && other.y2 == this.y2;
    }

    @Override
    public int hashCode() {
        int hashcode = 17;
        hashcode = hashcode * 31 + x;
        hashcode = hashcode * 31 + y;
        hashcode = hashcode * 31 + x2;
        hashcode = hashcode * 31 + y2;
        return hashcode;
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putInt( "x", x );
        bundle.putInt( "y", y );
        bundle.putInt( "x2", x2 );
        bundle.putInt( "y2", y2 );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        // everything done by the constructor
    }
}
