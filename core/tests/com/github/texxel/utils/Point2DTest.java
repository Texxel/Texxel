package com.github.texxel.utils;

import junit.framework.TestCase;

public class Point2DTest extends TestCase {

    public void testDirection() throws Exception {
        assertEquals( Point2D.DOWN_RIGHT, Point2D.direction( 1, -1 ) );
    }

    public void testDirectionTo() throws Exception {
        Point2D a = new Point2D( 4, 7 );
        Point2D b = new Point2D( 5, 6 );
        assertEquals( Point2D.DOWN_RIGHT, a.directionTo( b ) );
    }
}