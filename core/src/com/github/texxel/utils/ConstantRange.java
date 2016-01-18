package com.github.texxel.utils;

/**
 * The ConstantRange class always returns the same number
 */
public class ConstantRange implements Range {

    public static final ConstantRange ZERO = new ConstantRange( 0 );
    public static final ConstantRange ONE = new ConstantRange( 1 );
    private static final long serialVersionUID = -6847703670058918568L;

    private final float number;

    public ConstantRange( float number ) {
        this.number = number;
    }

    @Override
    public float get() {
        return number;
    }
}
