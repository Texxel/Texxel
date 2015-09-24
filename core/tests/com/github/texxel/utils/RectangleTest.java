package com.github.texxel.utils;

import junit.framework.TestCase;

public class RectangleTest extends TestCase {

    public void testFromSize() throws Exception {
        Rectangle rectangle = Rectangle.fromSize( -1, -4, 1, 2 );
        assertEquals( -1, rectangle.x );
        assertEquals( -4, rectangle.y );
        assertEquals( 1, rectangle.width );
        assertEquals( 2, rectangle.height );

        assertEquals( -1, rectangle.x2 );
        assertEquals( -3, rectangle.y2 );
    }

    public void testFromBounds() throws Exception {
        Rectangle rectangle = Rectangle.fromBounds( -1, -2, 1, 2 );
        assertEquals( -1, rectangle.x );
        assertEquals( -2, rectangle.y );
        assertEquals( 1, rectangle.x2 );
        assertEquals( 2, rectangle.y2 );

        assertEquals( 3, rectangle.width );
        assertEquals( 5, rectangle.height );
    }

    public void testArea() throws Exception {
        Rectangle rectangle = Rectangle.fromSize( 5, 4, 10, 6 );
        assertEquals( 10 * 6, rectangle.area() );
    }

    public void testIntersection() throws Exception {
        Rectangle a = Rectangle.fromBounds( 0, 2, 12, 22 );
        Rectangle b = Rectangle.fromBounds( 5, 8, 15, 11 );
        Rectangle i = a.intersection( b );
        assertEquals( 5, i.x );
        assertEquals( 8, i.y );
        assertEquals( 12, i.x2 );
        assertEquals( 11, i.y2 );

        a = Rectangle.fromBounds( -4, -5, -2, -1 );
        b = Rectangle.fromBounds( 5, 6, 8, 9 );
        i = a.intersection( b );
        assertNull( i );
    }

    public void testBounds() throws Exception {
        Rectangle a = Rectangle.fromBounds( 0, 2, 12, 22 );
        Rectangle b = Rectangle.fromBounds( 5, 8, 15, 11 );
        Rectangle i = a.bounds( b );
        assertEquals( 0, i.x );
        assertEquals( 2, i.y );
        assertEquals( 15, i.x2 );
        assertEquals( 22, i.y2 );
    }

    public void testShrunk() throws Exception {
        Rectangle r1 = Rectangle.fromBounds( 3, 5, 13, 15 );
        Rectangle r2 = r1.shrunk( 2 );
        assertEquals( 5, r2.x );
        assertEquals( 7, r2.y );
        assertEquals( 11, r2.x2 );
        assertEquals( 13, r2.y2 );

        boolean exceptionThrown = false;
        try {
            r1.shrunk( 8 );
        } catch ( IllegalArgumentException e ) {
            exceptionThrown = true;
        }
        assertTrue( exceptionThrown );
    }
}