package com.github.texxel.utils;

import java.util.Arrays;

/**
 * The Arrays2D class is designed to be alike to {@link java.util.Arrays} but for 2D arrays. All
 * methods will throw a NullPointerException if any of the passed arrays is null.
 */
public final class Arrays2D {

    /**
     * Copies the source array into the destination array
     * @param source the array to copy
     * @param dest the array to copy into
     * @throws NullPointerException if source
     * @throws IndexOutOfBoundsException if dest is smaller than source. Note: it will
     * not throw an IndexOutOfBoundsException if the dest is larger than source
     * @throws NullPointerException if source or dest are null
     */
    public static void copy( boolean[][] source, boolean[][] dest ) {
        for ( int i = 0 ; i < source.length; i++ ) {
            boolean[] array = source[i];
            System.arraycopy( array, 0, dest[i], 0, array.length );
        }
    }

    /**
     * Copies the source array into the destination array
     * @param source the array to copy
     * @param dest the array to copy into
     * @throws NullPointerException if source
     * @throws IndexOutOfBoundsException if dest is smaller than source. Note: it will
     * not throw an IndexOutOfBoundsException if the dest is larger than source
     * @throws NullPointerException if source or dest are null
     */
    public static void copy( int[][] source, int[][] dest ) {
        for ( int i = 0 ; i < source.length; i++ ) {
            int[] array = source[i];
            System.arraycopy( array, 0, dest[i], 0, array.length );
        }
    }

    /**
     * Copies the source array into the destination array
     * @param source the array to copy
     * @param dest the array to copy into
     * @throws NullPointerException if source
     * @throws IndexOutOfBoundsException if dest is smaller than source. Note: it will
     * not throw an IndexOutOfBoundsException if the dest is larger than source
     * @throws NullPointerException if source or dest are null
     */
    public static void copy( float[][] source, float[][] dest ) {
        for ( int i = 0 ; i < source.length; i++ ) {
            float[] array = source[i];
            System.arraycopy( array, 0, dest[i], 0, array.length );
        }
    }

    /**
     * Copies the source array into the destination array
     * @param source the array to copy
     * @param dest the array to copy into
     * @throws NullPointerException if source
     * @throws IndexOutOfBoundsException if dest is smaller than source. Note: it will
     * not throw an IndexOutOfBoundsException if the dest is larger than source
     * @throws NullPointerException if source or dest are null
     */
    public static void copy( double[][] source, double[][] dest ) {
        for ( int i = 0 ; i < source.length; i++ ) {
            double[] array = source[i];
            System.arraycopy( array, 0, dest[i], 0, array.length );
        }
    }

    /**
     * Fills an array with a single value
     * @param array the array to fill
     * @param value the value to fill the array with
     */
    public static void fill( int[][] array, int value ) {
        for ( int[] row : array )
            Arrays.fill( row, value );
    }

    /**
     * Fills an array with a single value
     * @param array the array to fill
     * @param value the value to fill the array with
     */
    public static void fill( boolean[][] array, boolean value ) {
        for ( boolean[] row : array )
            Arrays.fill( row, value );
    }

    /**
     * Fills an array with a single value
     * @param array the array to fill
     * @param value the value to fill the array with
     */
    public static void fill( float[][] array, float value ) {
        for ( float[] row : array )
            Arrays.fill( row, value );
    }

    /**
     * Fills an array with a single value
     * @param array the array to fill
     * @param value the value to fill the array with
     */
    public static void fill( double[][] array, double value ) {
        for ( double[] row : array )
            Arrays.fill( row, value );
    }

    /**
     * Tests if all values in the two arrays are equal
     * @param a array one
     * @param b array two
     * @return true if the two arrays are identical
     */
    public static boolean equals( boolean[][] a, boolean[][] b ) {
        if ( a == b )
            return true;
        if ( a == null || b == null )
            return false;
        int size = a.length;
        if ( b.length != a.length )
            return false;
        for ( int i = 0; i < size; i++ ) {
            if ( !Arrays.equals( a[i], b[i] ) )
                return false;
        }
        return true;
    }

    /**
     * Tests if all values in the two arrays are equal
     * @param a array one
     * @param b array two
     * @return true if the two arrays are identical
     */
    public static boolean equals( int[][] a, int[][] b ) {
        if ( a == b )
            return true;
        if ( a == null || b == null )
            return false;
        int size = a.length;
        if ( b.length != a.length )
            return false;
        for ( int i = 0; i < size; i++ ) {
            if ( !Arrays.equals( a[i], b[i] ) )
                return false;
        }
        return true;
    }

    /**
     * Tests if all values in the two arrays are equal
     * @param a array one
     * @param b array two
     * @return true if the two arrays are identical
     */
    public static boolean equals( float[][] a, float[][] b ) {
        if ( a == b )
            return true;
        if ( a == null || b == null )
            return false;
        int size = a.length;
        if ( b.length != a.length )
            return false;
        for ( int i = 0; i < size; i++ ) {
            if ( !Arrays.equals( a[i], b[i] ) )
                return false;
        }
        return true;
    }

    /**
     * Tests if all values in the two arrays are equal
     * @param a array one
     * @param b array two
     * @return true if the two arrays are identical
     */
    public static boolean equals( double[][] a, double[][] b ) {
        if ( a == b )
            return true;
        if ( a == null || b == null )
            return false;
        int size = a.length;
        if ( b.length != a.length )
            return false;
        for ( int i = 0; i < size; i++ ) {
            if ( !Arrays.equals( a[i], b[i] ) )
                return false;
        }
        return true;
    }


    /**
     * Creates a copy of a 2D array
     * @param source the array to copy
     * @return the copied array
     */
    public static boolean[][] copyOf( boolean[][] source ) {
        int length = source.length;
        boolean[][] clone = new boolean[length][];
        for ( int i = 0; i < length; i++ ) {
            boolean[] array = source[i];
            clone[i] = Arrays.copyOf( array, array.length );
        }
        return  clone;
    }

    /**
     * Creates a copy of a 2D array
     * @param source the array to copy
     * @return the copied array
     */
    public static int[][] copyOf( int[][] source ) {
        int length = source.length;
        int[][] clone = new int[length][];
        for ( int i = 0; i < length; i++ ) {
            int[] array = source[i];
            clone[i] = Arrays.copyOf( array, array.length );
        }
        return  clone;
    }

    /**
     * Creates a copy of a 2D array
     * @param source the array to copy
     * @return the copied array
     */
    public static float[][] copyOf( float[][] source ) {
        int length = source.length;
        float[][] clone = new float[length][];
        for ( int i = 0; i < length; i++ ) {
            float[] array = source[i];
            clone[i] = Arrays.copyOf( array, array.length );
        }
        return  clone;
    }

    /**
     * Creates a copy of a 2D array
     * @param source the array to copy
     * @return the copied array
     */
    public static double[][] copyOf( double[][] source ) {
        int length = source.length;
        double[][] clone = new double[length][];
        for ( int i = 0; i < length; i++ ) {
            double[] array = source[i];
            clone[i] = Arrays.copyOf( array, array.length );
        }
        return  clone;
    }

    /**
     * Tests if the given array is rectangular, i.e. every sub array is of the same length. A zero
     * length array is considered to be rectangular..
     * @param array the array to test for
     * @return true if the array is rectangular
     */
    public static boolean isRectangular( boolean[][] array ) {
        int width = array.length;
        if ( width == 0 )
            return true;
        int height = array[0].length;
        for ( int i = 1; i < width; i++ ) {
            if ( array[i].length != height )
                return false;
        }
        return true;
    }

    /**
     * Tests if the given array is rectangular, i.e. every sub array is of the same length. A zero
     * length array is considered to be rectangular.
     * @param array the array to test for
     * @return true if the array is rectangular
     */
    public static boolean isRectangular( int[][] array ) {
        int width = array.length;
        if ( width == 0 )
            return true;
        int height = array[0].length;
        for ( int i = 1; i < width; i++ ) {
            if ( array[i].length != height )
                return false;
        }
        return true;
    }

    /**
     * Tests if the given array is rectangular, i.e. every sub array is of the same length. A zero
     * length array is considered to be rectangular.
     * @param array the array to test for
     * @return true if the array is rectangular
     */
    public static boolean isRectangular( float[][] array ) {
        int width = array.length;
        if ( width == 0 )
            return true;
        int height = array[0].length;
        for ( int i = 1; i < width; i++ ) {
            if ( array[i].length != height )
                return false;
        }
        return true;
    }

    /**
     * Tests if the given array is rectangular, i.e. every sub array is of the same length. A zero
     * length array is considered to be rectangular.
     * @param array the array to test for
     * @return true if the array is rectangular
     */
    public static boolean isRectangular( double[][] array ) {
        int width = array.length;
        if ( width == 0 )
            return true;
        int height = array[0].length;
        for ( int i = 1; i < width; i++ ) {
            if ( array[i].length != height )
                return false;
        }
        return true;
    }

    /**
     * Tests if the given array is rectangular, i.e. every sub array is of the same length. A zero
     * length array is considered to be rectangular.
     * @param array the array to test for
     * @return true if the array is rectangular
     */
    public static boolean isRectangular( long[][] array ) {
        int width = array.length;
        if ( width == 0 )
            return true;
        int height = array[0].length;
        for ( int i = 1; i < width; i++ ) {
            if ( array[i].length != height )
                return false;
        }
        return true;
    }

    /**
     * Tests if the passed array contains the point (x,y)
     * @param array the array to test
     * @param x the x point to test
     * @param y the y point to test
     * @return true if point (x,y) is inside
     */
    public static boolean contains( boolean[][] array, int x, int y ) {
        int width = array.length;
        if ( x < 0 || x >= width )
            return false;
        int height = array[x].length;
        if ( y < 0 || y >= height )
            return false;
        return true;
    }

    /**
     * Tests if the passed array contains the point (x,y)
     * @param array the array to test
     * @param x the x point to test
     * @param y the y point to test
     * @return true if point (x,y) is inside
     */
    public static boolean contains( int[][] array, int x, int y ) {
        int width = array.length;
        if ( x < 0 || x >= width )
            return false;
        int height = array[x].length;
        if ( y < 0 || y >= height )
            return false;
        return true;
    }

    /**
     * Tests if the passed array contains the point (x,y)
     * @param array the array to test
     * @param x the x point to test
     * @param y the y point to test
     * @return true if point (x,y) is inside
     */
    public static boolean contains( float[][] array, int x, int y ) {
        int width = array.length;
        if ( x < 0 || x >= width )
            return false;
        int height = array[x].length;
        if ( y < 0 || y >= height )
            return false;
        return true;
    }

    /**
     * Tests if the passed array contains the point (x,y)
     * @param array the array to test
     * @param x the x point to test
     * @param y the y point to test
     * @return true if point (x,y) is inside
     */
    public static boolean contains( double[][] array, int x, int y ) {
        int width = array.length;
        if ( x < 0 || x >= width )
            return false;
        int height = array[x].length;
        if ( y < 0 || y >= height )
            return false;
        return true;
    }

    /**
     * Tests if the passed array contains the point (x,y)
     * @param array the array to test
     * @param x the x point to test
     * @param y the y point to test
     * @return true if point (x,y) is inside
     */
    public static boolean contains( long[][] array, int x, int y ) {
        int width = array.length;
        if ( x < 0 || x >= width )
            return false;
        int height = array[x].length;
        if ( y < 0 || y >= height )
            return false;
        return true;
    }
}
