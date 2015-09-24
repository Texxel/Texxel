package com.github.texxel.utils;

import junit.framework.TestCase;

public class Arrays2DTest extends TestCase {

    // only bothered to test int versions - all others should just work as they are just copied

    public void testContains() {
        int[][] a = new int[][] { { 1, 2, 3 }, { 1, 2 } };
        assertTrue( Arrays2D.contains( a, 0, 0 ) );
        assertTrue( Arrays2D.contains( a, 0, 2 ) );
        assertTrue( Arrays2D.contains( a, 1, 1 ) );
        assertFalse( Arrays2D.contains( a, 1, 2 ) );

        int[][] b = new int[0][0];
        assertFalse( Arrays2D.contains( b, 0, 0 ) );
    }

    public void testRectangular() {
        int[][] a = new int[][] { { 1, 2, 3 }, { 1, 2, 3 } };
        int[][] b = new int[][] { { 1, 2, 3 }, { 1, 2 } };
        int[][] c = new int[][] { };
        int[][] d = new int[][] { { 1, 2, 3 } };

        assertTrue( Arrays2D.isRectangular( a ) );
        assertFalse( Arrays2D.isRectangular( b ) );
        assertTrue( Arrays2D.isRectangular( c ) );
        assertTrue( Arrays2D.isRectangular( d ) );
        try {
            Arrays2D.isRectangular( (int[][])null );
            fail();
        } catch ( NullPointerException ignored ) {
        }
    }

    public void testEquals() throws Exception {
        int[][] a = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
        int[][] b = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
        int[][] c = new int[][] { { 1, 2, 3 }, { 8, 5, 6 } };
        int[][] d = new int[][] { { 1, 2, 3 }, { 4, 5, 6, 7 } };
        assertTrue( Arrays2D.equals( a, a ) );
        assertTrue( Arrays2D.equals( a, b ) );
        assertFalse( Arrays2D.equals( a, c ) );
        assertFalse( Arrays2D.equals( a, d ) );
        assertFalse( Arrays2D.equals( d, a ) );
    }

    public void testCopy() throws Exception {
        int[][] source = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
        int[][] dest = new int[2][3];
        Arrays2D.copy( source, dest );
        assertTrue( Arrays2D.equals( source, dest ) );
    }

    public void testFill() throws Exception {
        int[][] array = new int[3][2];
        Arrays2D.fill( array, 5 );
        assertEquals( 5, array[0][0] );
        assertEquals( 5, array[2][1] );
        assertEquals( 5, array[0][1] );
    }

    public void testClone() throws Exception {
        int[][] a = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
        int[][] b = Arrays2D.copyOf( a );
        assertTrue( Arrays2D.equals( a, b ) );
        assertFalse( a == b );
    }


}