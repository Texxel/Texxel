package com.github.texxel.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Random {

    private static java.util.Random random = new java.util.Random();

    /**
     * Generates a random number between [0, max). If max == 0, then 0 is returned.
     * @param max the maximum number (exclusive)
     * @return the randomly generated number
     * @throws IllegalArgumentException if max is smaller than 0
     */
    public static int Int( int max ) {
        if ( max == 0 )
            return 0;
        try {
            return random.nextInt( max );
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException( "max cannot be smaller than 0. Passed max=" + max, e );
        }
    }

    /**
     * Generates a random integer between [min, max). If max == min, then the min/max is returned.
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return the randomly generated number
     * @throws IllegalArgumentException if <code>max < min</code>
     */
    public static int Int( int min, int max ) {
        if ( min == max )
            return min;
        try {
            return random.nextInt( max - min ) + min;
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException( "min must be smaller than max. Passed min=" + min
                + ", max=" + max + ".", e );
        }
    }

    /**
     * Generates a random number in the range of [0,1)
     * @return the random number
     */
    public static float Float() {
        return random.nextFloat();
    }

    /**
     * Generates a random number between [0, max). If max == 0, then 0 is returned.
     * @param max the maximum random number (exclusive)
     * @return the randomly generated number
     * @throws IllegalArgumentException if max < 0;
     */
    public static float Float( float max ) {
        if ( max == 0 )
            return 0;
        if ( max < 0 )
            throw new IllegalArgumentException( "max cannot be smaller than 0. Passed max=" + max );
        return random.nextFloat() * max;
    }

    /**
     * Generates a random number between [min, max). If min == max, then max (or min) is returned.
     * @param min the minimum (inclusive)
     * @param max the maximum (exclusive)
     * @return the randomly generated number
     * @throws IllegalArgumentException if max < min
     */
    public static float Float( float min, float max ) {
        if ( max < min )
            throw new IllegalArgumentException( "max cannot be smaller than min. Passed: min=" + min
                + ", max=" + max );
        return random.nextFloat() * ( max - min ) + min;
    }

    /**
     * Generates a number between min and max, but with a greater probability to be in the center than
     * the edges. This is actually a triangular distribution
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return the randomly generated number
     * @throws IllegalArgumentException if max < min
     */
    public static float normalFloat( float min, float max ) {
        if ( max < min )
            throw new IllegalArgumentException( "max cannot be smaller than min. Passed: min=" + min
                    + ", max=" + max );
        return min + ( random.nextFloat() + random.nextFloat() ) / 2 * ( max - min );
    }

    /**
     * Generates a random number between min and max but with a greater likely hood to be in the
     * center. The distribution is not normal but trianglular
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return the randomly generated number
     */

    public static int normalInt( int min, int max ) {
        return min + (int)( random.nextFloat() + random.nextFloat() ) / 2 * ( max - min );
    }

    /**
     * Returns true with a probability of {@code 1/sides}, false otherwise. This is much like rolling
     * as dice with n sides and checking if the result is a n. If sides is 0, then false is always
     * returned. If sides is == 1, then true will always be returned.
     * @param sides the amount of sides on the dice to roll
     * @throws IllegalArgumentException if sides < 0
     */
    public static boolean roll( int sides ) {
        if ( sides == 0 )
            return false;
        return Random.Int( sides ) == 0;
    }

    /**
     * Selects an element from a collection at random.
     * @param collection the collection to select from
     * @return the random element or null if the list is empty
     * @throws NullPointerException if the collection is null
     */
    public static <T> T select( Collection<? extends T> collection ) {
        if ( collection instanceof List )
            return selectList( (List<? extends T>) collection );
        int size = collection.size();
        if ( size > 0 )
            return (T)collection.toArray()[Int( size )];
        else
            return null;
    }

    /**
     * An optimised version of {@link #select(Collection)} for lists
     */
    private static <T> T selectList( List<? extends T> collection ) {
        int size = collection.size();
        if ( size == 0 )
            return null;
        return collection.get( Int( size ) );
    }

    /**
     * Shuffles the positions of all elements in the list.
     * @param collection the collection to shuffle
     * @throws UnsupportedOperationException if the list is immutable
     * @throws NullPointerException if the list is null
     */
    public static <T> void shuffle( List<T> collection ) {
        Collections.shuffle( collection, random );
    }

}
