package com.github.texxel.utils;

/**
 * Some classes for asserting various states of variables
 */
public class Assert {

    /**
     * Asserts that the object is not null. If the object is null, then a NullPointerException is thrown
     * @param obj the object to test
     * @param errorMessage the message to put into error message if the object is null
     * @param <T> the type of the object to test
     * @return obj
     */
    public static <T> T nonnull( T obj, String errorMessage ) {
        if ( obj == null )
            throw new NullPointerException( errorMessage );
        else
            return obj;
    }

    /**
     * Asserts that an integer is inside a range. The range is inclusive.
     * @param n the integer to test
     * @param min the min number allowed
     * @param max the max number allowed
     * @param errorMessage the message to give if the number is out of range
     * @return n
     */
    public static int between( int n, int min, int max, String errorMessage ) {
        if ( n <= min || n >= max )
            throw new IllegalArgumentException( errorMessage );
        return n;
    }

    /**
     * Asserts a number is larger or equal to another number
     * @param n the number to test
     * @param min the min value the number can be
     * @param errorMessage the error message
     * @return n
     */
    public static int over( int n, int min, String errorMessage ) {
        if ( n < min )
            throw new IllegalArgumentException( errorMessage );
        return n;
    }

    /**
     * Asserts a number is smaller or equal to another number
     * @param n the number to test
     * @param max the max value the number can be
     * @param errorMessage the error message
     * @return n
     */
    public static int under( int n, int max, String errorMessage ) {
        if ( n > max )
            throw new IllegalArgumentException( errorMessage );
        return n;
    }

}
