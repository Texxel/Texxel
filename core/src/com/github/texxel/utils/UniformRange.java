package com.github.texxel.utils;

/**
 * The uniform range class will generate random in a range. Every value in the range is just as
 * likely to be chosen as any other value.
 */
public class UniformRange implements Range {

    private final float min, max;

    /**
     * Creates a uniform range between a lower and upper bound
     * @param min the min number possible
     * @param max the max value possible
     */
    public UniformRange( float min, float max ) {
        this.min = min;
        this.max = max;
    }

    @Override
    public float get() {
        return Random.Float( min, max );
    }

    /**
     * Gets the minimum value possible
     * @return the min value
     */
    public float getMin() {
        return min;
    }

    /**
     * Gets the maximum value that can be returned
     * @return the max value possible
     */
    public float getMax() {
        return max;
    }
}
