package com.github.texxel.utils;

/**
 * The TriangleRange class has tends to generate numbers in the middle of the range.
 */
public class TriangleRange implements Range {
    private static final long serialVersionUID = -5728134436309688004L;

    // TODO let users set the center value

    public final float min, max;

    public TriangleRange( float min, float max ) {
        this.max = max;
        this.min = min;
    }

    @Override
    public float get() {
        return Random.normalFloat( min, max );
    }

    /**
     * Gets the minimum value possible
     * @return the min value
     */
    public float getMin() {
        return min;
    }

    /**
     * Gets the maximum value possible
     * @return the max value
     */
    public float getMax() {
        return max;
    }
}
