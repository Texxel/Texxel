package com.github.texxel.utils;

/**
 * The Additive range adds the multiple of a bunch of other ranges
 */
public class AdditiveRange implements Range {

    private static final long serialVersionUID = -2994812707218865214L;
    private final Range[] others;

    /**
     * Constructs a range that will add the other ranges.
     * @param others the other ranges to add (array is copied before use)
     */
    public AdditiveRange( Range ... others ) {
        int size = others.length;
        this.others = new Range[size];
        System.arraycopy( others, 0, this.others, 0, size );
    }

    @Override
    public float get() {
        float sum = 0;
        for( Range range : others )
            sum += range.get();
        return sum;
    }
}
